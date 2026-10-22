package com.example.miruta.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.miruta.ui.screens.*
import com.example.miruta.viewmodel.ActivityViewModel
import com.example.miruta.viewmodel.PreferencesViewModel

@Composable
fun AppNavigation(nav:NavHostController, initial:String, activityVM:ActivityViewModel, prefsVM:PreferencesViewModel) {
    // TODO-13: evaluar navegación y backstack de acuerdo con las pruebas.
    // Solo regresa si hay una pantalla anterior: así un doble toque en «Volver» no saca también a Home de la pila.
    val volver: ()->Unit = { if (nav.previousBackStackEntry != null) nav.popBackStack() }
    NavHost(navController=nav,startDestination=initial) {
        composable(Routes.ONBOARDING) {
            // popUpTo(ONBOARDING) inclusive: al presionar Atrás en Home no se vuelve al onboarding.
            OnboardingScreen { prefsVM.finalizarOnboarding(); nav.navigate(Routes.HOME) { popUpTo(Routes.ONBOARDING){inclusive=true}; launchSingleTop=true } }
        }
        // launchSingleTop evita abrir dos veces la misma pantalla con un doble toque.
        composable(Routes.HOME) { HomeScreen(activityVM,prefsVM,onAgregar={nav.navigate(Routes.ADD){launchSingleTop=true}},onDetalle={nav.navigate(Routes.detalle(it)){launchSingleTop=true}},onAjustes={nav.navigate(Routes.SETTINGS){launchSingleTop=true}}) }
        composable(Routes.ADD) { AddActivityScreen(activityVM,volver) }
        composable(Routes.DETAIL) { entry ->
            // El id viaja en la ruta "detail/{id}" como texto y aquí se convierte a Int (-1 si no es válido).
            val id=entry.arguments?.getString("id")?.toIntOrNull() ?: -1
            DetailScreen(id,activityVM,volver)
        }
        composable(Routes.SETTINGS) { SettingsScreen(prefsVM,volver) }
    }
}
