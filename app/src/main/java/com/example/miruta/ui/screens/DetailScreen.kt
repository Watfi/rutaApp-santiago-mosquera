package com.example.miruta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.miruta.ui.theme.colorPrioridad
import com.example.miruta.ui.theme.textoPrioridad
import com.example.miruta.viewmodel.ActivityViewModel

@Composable
fun DetailScreen(id:Int, vm:ActivityViewModel, onVolver:()->Unit) {
    val lista by vm.actividades.collectAsStateWithLifecycle()
    val actividad = lista.find { it.id==id }
    Column(Modifier.fillMaxSize().padding(20.dp), verticalArrangement=Arrangement.spacedBy(12.dp)) {
        // TODO-11: mejorar manejo de datos inválidos y diseño.
        if(actividad==null) {
            // id inválido (-1) o que no existe en la lista
            Text("Actividad no encontrada", style=MaterialTheme.typography.headlineSmall, color=MaterialTheme.colorScheme.error)
            Text("El identificador recibido ($id) no corresponde a ninguna actividad.")
        }
        else {
            Text(actividad.titulo, style=MaterialTheme.typography.headlineSmall, color=MaterialTheme.colorScheme.primary)
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement=Arrangement.spacedBy(8.dp)) {
                    Text("Descripción", style=MaterialTheme.typography.labelLarge)
                    Text(actividad.descripcion.ifBlank { "Sin descripción" })
                    Text("Categoría: ${actividad.categoria}")
                    Text("Prioridad: ${actividad.prioridad} (${textoPrioridad(actividad.prioridad)})", color=colorPrioridad(actividad.prioridad))
                    Text("Estado: " + if(actividad.completada) "Completada" else "Pendiente",
                        color=if(actividad.completada) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Button(onClick={vm.cambiarEstado(id)}) { Text(if(actividad.completada) "Marcar como pendiente" else "Marcar como completada") }
        }
        OutlinedButton(onClick=onVolver) { Text("Volver") }
    }
}
