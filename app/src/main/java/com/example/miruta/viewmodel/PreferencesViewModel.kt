package com.example.miruta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miruta.data.local.PreferencesManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PreferencesViewModel(private val manager:PreferencesManager): ViewModel() {
    val onboarding = manager.onboardingCompletado.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
    val nombre = manager.nombre.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Estudiante")
    val oscuro = manager.oscuro.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
    fun finalizarOnboarding() = viewModelScope.launch { manager.finalizarOnboarding() }
    fun guardarNombre(valor:String) = viewModelScope.launch { manager.guardarNombre(valor) }
    fun guardarOscuro(valor:Boolean) = viewModelScope.launch { manager.guardarModoOscuro(valor) }
}
