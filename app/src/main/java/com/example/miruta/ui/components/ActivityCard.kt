package com.example.miruta.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.miruta.model.Actividad
import com.example.miruta.ui.theme.colorPrioridad
import com.example.miruta.ui.theme.textoPrioridad

@Composable
fun ActivityCard(actividad:Actividad, onClick:()->Unit) {
    Card(Modifier.fillMaxWidth().padding(vertical=5.dp).clickable(onClick=onClick)) {
        // TODO-07: mejorar diseño visual según la prioridad.
        Row(Modifier.padding(16.dp), verticalAlignment=Alignment.CenterVertically) {
            // Círculo con el color de la prioridad (verde = baja, amarillo = media, rojo = alta)
            Box(Modifier.size(14.dp).background(colorPrioridad(actividad.prioridad), CircleShape))
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(actividad.titulo, style=MaterialTheme.typography.titleMedium,
                    textDecoration=if (actividad.completada) TextDecoration.LineThrough else null)
                Text("${actividad.categoria} · Prioridad ${actividad.prioridad} (${textoPrioridad(actividad.prioridad)})",
                    color=colorPrioridad(actividad.prioridad))
            }
            Text(if (actividad.completada) "Completada" else "Pendiente",
                color=if (actividad.completada) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant,
                style=MaterialTheme.typography.labelLarge)
        }
    }
}
