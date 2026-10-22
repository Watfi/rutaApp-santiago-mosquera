package com.example.miruta.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.miruta.viewmodel.PreferencesViewModel

@Composable
fun SettingsScreen(vm:PreferencesViewModel, onVolver:()->Unit) {
    val actual by vm.nombre.collectAsStateWithLifecycle()
    val oscuro by vm.oscuro.collectAsStateWithLifecycle()
    var nuevo by remember(actual) { mutableStateOf(actual) }
    var mensaje by remember { mutableStateOf("") }
    var esError by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().padding(20.dp), verticalArrangement=Arrangement.spacedBy(12.dp)) {
        Text("Preferencias", style=MaterialTheme.typography.headlineSmall, color=MaterialTheme.colorScheme.primary)
        OutlinedTextField(nuevo,{nuevo=it; mensaje=""},label={Text("Tu nombre")}, singleLine=true, isError=esError && mensaje.isNotBlank(), modifier=Modifier.fillMaxWidth())
        // TODO-12: validar nombre y mejorar composición visual.
        Button(onClick={
            val limpio = nuevo.trim()
            when {
                limpio.isEmpty() -> { esError=true; mensaje="El nombre no puede estar vacío" }
                limpio.length > 20 -> { esError=true; mensaje="Máximo 20 caracteres" }
                else -> { vm.guardarNombre(limpio); esError=false; mensaje="Nombre guardado" }
            }
        }) { Text("Guardar nombre") }
        if (mensaje.isNotBlank()) Text(mensaje, color=if (esError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary)
        HorizontalDivider()
        Row(Modifier.fillMaxWidth(), verticalAlignment=Alignment.CenterVertically) {
            Text("Modo oscuro", Modifier.weight(1f))
            Switch(checked=oscuro,onCheckedChange={vm.guardarOscuro(it)})
        }
        OutlinedButton(onClick=onVolver) { Text("Volver") }
    }
}
