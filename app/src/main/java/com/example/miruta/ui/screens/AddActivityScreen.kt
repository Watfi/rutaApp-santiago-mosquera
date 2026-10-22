package com.example.miruta.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.miruta.ui.theme.colorPrioridad
import com.example.miruta.ui.theme.textoPrioridad
import com.example.miruta.viewmodel.ActivityViewModel

@Composable
fun AddActivityScreen(vm:ActivityViewModel, onVolver:()->Unit) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("Académica") }
    var prioridad by remember { mutableIntStateOf(1) }
    val cargando by vm.cargando.collectAsStateWithLifecycle()
    val error by vm.error.collectAsStateWithLifecycle()
    val categorias = listOf("Académica", "Personal", "Trabajo")
    // Al entrar a la pantalla se borra un error que haya quedado de un intento anterior.
    LaunchedEffect(Unit) { vm.limpiarError() }
    // Mientras se guarda no se permite salir con Atrás (evita volver dos veces al terminar).
    BackHandler(enabled=cargando) { }
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement=Arrangement.spacedBy(12.dp)) {
        Text("Nueva actividad", style=MaterialTheme.typography.headlineSmall, color=MaterialTheme.colorScheme.primary)
        OutlinedTextField(titulo,{titulo=it; vm.limpiarError()}, label={Text("Título")}, modifier=Modifier.fillMaxWidth(),
            enabled=!cargando, singleLine=true, isError=error.isNotBlank())
        OutlinedTextField(descripcion,{descripcion=it}, label={Text("Descripción")}, modifier=Modifier.fillMaxWidth(), enabled=!cargando)
        // TODO-10: reglas de errores, categorías y estados visuales.
        // Categoría: se escoge de una lista fija en lugar de escribirla.
        Text("Categoría: $categoria")
        Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) {
            categorias.forEach { c -> FilterChip(selected=categoria==c, onClick={categoria=c}, label={Text(c)}, enabled=!cargando) }
        }
        Text("Prioridad: $prioridad (${textoPrioridad(prioridad)})", color=colorPrioridad(prioridad))
        Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) {
            (1..3).forEach { p -> FilterChip(selected=prioridad==p, onClick={prioridad=p}, label={Text("$p · ${textoPrioridad(p)}")}, enabled=!cargando) }
        }
        if(error.isNotBlank()) Text(error, color=MaterialTheme.colorScheme.error)
        Row(verticalAlignment=Alignment.CenterVertically, horizontalArrangement=Arrangement.spacedBy(12.dp)) {
            Button(enabled=!cargando, onClick={vm.registrar(titulo,descripcion,categoria,prioridad,onVolver)}) { Text(if(cargando) "Guardando…" else "Registrar") }
            OutlinedButton(enabled=!cargando, onClick=onVolver) { Text("Cancelar") }
            if(cargando) CircularProgressIndicator(Modifier.size(28.dp)) // estado de carga
        }
    }
}
