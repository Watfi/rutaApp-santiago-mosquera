package com.example.miruta.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnboardingScreen(onTerminar:()->Unit) {
    var pagina by remember { mutableIntStateOf(0) }
    val titulos = listOf("Bienvenido a MiRuta", "Organiza tus actividades", "Personaliza la experiencia")
    val descripciones = listOf("Organiza tu día: entregas, lecturas, talleres y compromisos en un solo lugar.",
        "Consulta pendientes y prioridades, y marca cada actividad como completada.",
        "Configura tu nombre y el modo oscuro; MiRuta los recuerda al volver a abrir.")
    val iconos = listOf("📚", "✅", "⚙️") // ilustración sencilla de cada página
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement=Arrangement.Center, horizontalAlignment=Alignment.CenterHorizontally) {
        Text(iconos[pagina], fontSize=72.sp)
        Spacer(Modifier.height(24.dp))
        Text(titulos[pagina], style=MaterialTheme.typography.headlineMedium, color=MaterialTheme.colorScheme.primary, textAlign=TextAlign.Center)
        Spacer(Modifier.height(16.dp)); Text(descripciones[pagina], textAlign=TextAlign.Center); Spacer(Modifier.height(28.dp))
        // TODO-08: ilustración/indicador visual y botón anterior.
        // Indicador: un punto por página; el de la página actual es más ancho y del color principal.
        Row(horizontalArrangement=Arrangement.spacedBy(6.dp)) {
            repeat(3) { i ->
                Box(Modifier.height(10.dp).width(if (i==pagina) 24.dp else 10.dp)
                    .background(if (i==pagina) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant, CircleShape))
            }
        }
        Spacer(Modifier.height(28.dp))
        Row(horizontalArrangement=Arrangement.spacedBy(12.dp)) {
            if (pagina>0) OutlinedButton(onClick={ pagina-- }) { Text("Anterior") }
            Button(onClick={ if(pagina<2) pagina++ else onTerminar() }) { Text(if(pagina==2) "Empezar" else "Siguiente") }
        }
    }
}
