package com.example.miruta.data.repository

import com.example.miruta.model.Actividad

class ActivityRepository {
    private val actividades = mutableListOf<Actividad>()
    fun obtenerActividades(): List<Actividad> = actividades.toList()
    fun buscarActividad(id: Int): Actividad? = actividades.find { it.id == id }
    fun agregar(actividad: Actividad): Boolean {
        if (actividades.any { it.id == actividad.id }) return false
        if (existeTitulo(actividad.titulo)) return false // MOV1-01: no se aceptan títulos repetidos
        actividades.add(actividad)
        return true
    }
    fun cambiarEstado(id: Int): Boolean {
        val indice = actividades.indexOfFirst { it.id == id }
        if (indice < 0) return false
        val actual = actividades[indice]
        actividades[indice] = actual.copy(completada = !actual.completada)
        return true
    }
    // TODO-ESTUDIANTE: añadir las reglas del reto asignado en la capa apropiada.
    // RETO MOV1-01: evitar títulos duplicados.
    // Se normaliza el título (sin espacios al inicio/final y en minúsculas) antes de comparar,
    // así «Examen» y « examen » se consideran el mismo título.
    private fun normalizar(titulo: String): String = titulo.trim().lowercase()
    fun existeTitulo(titulo: String): Boolean = actividades.any { normalizar(it.titulo) == normalizar(titulo) }
}
