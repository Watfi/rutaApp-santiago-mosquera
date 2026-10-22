package com.example.miruta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.miruta.data.local.PreferencesManager
import com.example.miruta.data.repository.ActivityRepository
import com.example.miruta.navigation.AppNavigation
import com.example.miruta.navigation.Routes
import com.example.miruta.ui.theme.MiRutaTheme
import com.example.miruta.viewmodel.ActivityViewModel
import com.example.miruta.viewmodel.PreferencesViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MainActivity:ComponentActivity() {
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        val manager=PreferencesManager(applicationContext)
        val repo=ActivityRepository()
        setContent {
            val activityVM:ActivityViewModel = viewModel(factory=object:ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST") override fun <T:ViewModel> create(modelClass:Class<T>):T = ActivityViewModel(repo) as T
            })
            val prefsVM:PreferencesViewModel = viewModel(factory=object:ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST") override fun <T:ViewModel> create(modelClass:Class<T>):T = PreferencesViewModel(manager) as T
            })
            val oscuro by prefsVM.oscuro.collectAsStateWithLifecycle()
            // Se decide destino solo al obtener el primer valor real de DataStore.
            var initial by remember { mutableStateOf<String?>(null) }
            LaunchedEffect(Unit) { initial=if(manager.onboardingCompletado.first()) Routes.HOME else Routes.ONBOARDING }
            MiRutaTheme(oscuro=oscuro) { initial?.let { AppNavigation(rememberNavController(),it,activityVM,prefsVM) } }
        }
    }
}
