package com.example.miruta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miruta.data.repository.ActivityRepository
import com.example.miruta.model.Actividad
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ActivityViewModel(private val repository: ActivityRepository): ViewModel() {
    private val _actividades = MutableStateFlow(repository.obtenerActividades())
    val actividades = _actividades.asStateFlow()
    private val _cargando = MutableStateFlow(false)
    val cargando = _cargando.asStateFlow()
    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    fun registrar(titulo:String, descripcion:String, categoria:String, prioridad:Int, alTerminar:()->Unit) {
        if (_cargando.value) return // evita dobles pulsaciones mientras se guarda
        _error.value = ""
        // TODO-03: validar título, prioridad y la regla particular del estudiante.
        if (titulo.isBlank()) { _error.value = "El título es obligatorio"; return }
        if (titulo.trim().length < 3) { _error.value = "El título debe tener al menos 3 caracteres"; return }
        if (categoria.isBlank()) { _error.value = "Selecciona una categoría"; return }
        if (prioridad !in 1..3) { _error.value = "La prioridad debe ser 1, 2 o 3"; return }
        // Regla MOV1-01: rechazar títulos repetidos (ignora mayúsculas y espacios al inicio/final).
        if (repository.existeTitulo(titulo)) { _error.value = "Ya existe una actividad con ese título"; return }
        _cargando.value = true // se marca antes de lanzar la corrutina para bloquear un segundo clic
        viewModelScope.launch {
            try {
                delay(1500) // simula una operación lenta (como guardar en un servidor)
                val nueva = Actividad((repository.obtenerActividades().maxOfOrNull { it.id } ?: 0)+1, titulo.trim(), descripcion.trim(), categoria, prioridad)
                // TODO-04: gestionar resultado de agregar y actualizar el StateFlow.
                val agregado = repository.agregar(nueva)
                if (agregado) {
                    _actividades.value = repository.obtenerActividades()
                    alTerminar()
                } else {
                    _error.value = "Ya existe una actividad con ese título"
                }
            } finally { _cargando.value = false }
        }
    }
    fun cambiarEstado(id: Int) {
        // TODO-05: actualizar la lista observable tras cambiar el estado.
        if (repository.cambiarEstado(id)) _actividades.value = repository.obtenerActividades()
    }
    fun limpiarError() { _error.value = "" }
}
