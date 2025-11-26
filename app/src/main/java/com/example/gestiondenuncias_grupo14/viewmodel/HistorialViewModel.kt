package com.example.gestiondenuncias_grupo14.viewmodel

import FormularioHistorialDto
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestiondenuncias_grupo14.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistorialViewModel : ViewModel() {

    private val _historial = MutableStateFlow<List<FormularioHistorialDto>>(emptyList())
    val historial: StateFlow<List<FormularioHistorialDto>> = _historial

    fun cargarHistorial() {
        viewModelScope.launch {
            try {
                val lista = RetrofitClient.apiService.getHistorial()
                _historial.value = lista
            } catch (e: Exception) {
                Log.e("Historial", "Error al cargar historial: ${e.localizedMessage}", e)
            }
        }
    }

    fun eliminarFormulario(id: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.deleteHistorial(id)
                if (response.isSuccessful) {
                    cargarHistorial() // recargar lista después de eliminar
                    onResult(true)
                } else {
                    Log.e("Historial", "Error al eliminar: Código ${response.code()}")
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("Historial", "Excepción al eliminar: ${e.localizedMessage}", e)
                onResult(false)
            }
        }
    }
}
