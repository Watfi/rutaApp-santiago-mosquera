package com.example.miruta.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MiRutaTheme(oscuro:Boolean, content:@Composable ()->Unit) {
    // TODO-06: enriquecer colores de prioridades y estilos.
    val colores = if (oscuro) darkColorScheme(primary = AzulClaro, secondary = VerdeClaro, background = FondoOscuro, surface = FondoOscuro, surfaceVariant = TarjetaOscura, error = RojoPrioridad)
                  else lightColorScheme(primary = AzulPrincipal, secondary = VerdeAccion, background = FondoClaro, surface = FondoClaro, error = RojoPrioridad)
    MaterialTheme(colorScheme = colores, typography = MiRutaTypography) {
        // Surface pinta el fondo según el tema; safeDrawingPadding evita que el contenido quede bajo la barra de estado.
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Box(Modifier.safeDrawingPadding()) { content() }
        }
    }
}

// Color que representa cada prioridad: 1 = Baja, 2 = Media, 3 = Alta.
fun colorPrioridad(prioridad:Int): Color = when (prioridad) {
    3 -> RojoPrioridad
    2 -> AmarilloPrioridad
    else -> VerdeAccion
}

fun textoPrioridad(prioridad:Int): String = when (prioridad) {
    3 -> "Alta"
    2 -> "Media"
    else -> "Baja"
}
