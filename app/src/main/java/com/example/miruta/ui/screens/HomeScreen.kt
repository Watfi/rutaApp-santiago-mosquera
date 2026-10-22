package com.example.miruta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.miruta.ui.components.ActivityCard
import com.example.miruta.viewmodel.ActivityViewModel
import com.example.miruta.viewmodel.PreferencesViewModel

@Composable
fun HomeScreen(vm:ActivityViewModel, prefs:PreferencesViewModel, onAgregar:()->Unit, onDetalle:(Int)->Unit, onAjustes:()->Unit) {
    val actividades by vm.actividades.collectAsStateWithLifecycle()
    val nombre by prefs.nombre.collectAsStateWithLifecycle()
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Hola, $nombre", style=MaterialTheme.typography.headlineMedium, color=MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) { Button(onClick=onAgregar) { Text("Nueva") }; OutlinedButton(onClick=onAjustes) { Text("Preferencias") } }
        // TODO-09: desarrollar la interfaz solicitada por su variante.
        // Resumen de la lista y recordatorio de la regla MOV1-01 (los títulos no se pueden repetir).
        val pendientes = actividades.count { !it.completada }
        Spacer(Modifier.height(12.dp))
        Text("${actividades.size} actividades · $pendientes pendientes · ${actividades.size - pendientes} completadas", style=MaterialTheme.typography.bodyMedium)
        Text("Cada actividad debe tener un título único.", style=MaterialTheme.typography.bodySmall, color=MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(8.dp))
        if (actividades.isEmpty()) {
            // Estado vacío
            Column(Modifier.fillMaxWidth().padding(top=48.dp), horizontalAlignment=Alignment.CenterHorizontally) {
                Text("No hay actividades registradas", style=MaterialTheme.typography.titleMedium)
                Text("Pulsa «Nueva» para crear la primera.", textAlign=TextAlign.Center, color=MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        LazyColumn { items(actividades, key={it.id}) { item -> ActivityCard(item) { onDetalle(item.id) } } }
    }
}
