package com.example.miruta.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("miruta_preferences")
class PreferencesManager(private val context: Context) {
    companion object {
        private val ONBOARDING = booleanPreferencesKey("onboarding")
        private val NOMBRE = stringPreferencesKey("nombre")
        private val OSCURO = booleanPreferencesKey("oscuro")
    }
    val onboardingCompletado: Flow<Boolean> = context.dataStore.data.map { it[ONBOARDING] ?: false }
    val nombre: Flow<String> = context.dataStore.data.map { it[NOMBRE] ?: "Estudiante" }
    val oscuro: Flow<Boolean> = context.dataStore.data.map { it[OSCURO] ?: false }
    suspend fun finalizarOnboarding() { context.dataStore.edit { it[ONBOARDING] = true } }
    // TODO-ESTUDIANTE: implementar las dos funciones de persistencia.
    // TODO-01: edit abre una transacción y guarda el nombre con la clave NOMBRE.
    suspend fun guardarNombre(valor: String) { context.dataStore.edit { it[NOMBRE] = valor } }
    // TODO-02: edit guarda true/false con la clave OSCURO.
    suspend fun guardarModoOscuro(valor: Boolean) { context.dataStore.edit { it[OSCURO] = valor } }
}
