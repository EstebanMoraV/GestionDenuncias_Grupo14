package com.example.gestiondenuncias_grupo14.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class EvidenciaState(
    val evidenciaExistente: Boolean = false,
    val otrosAntecedentes: Boolean = false,
    val informadaPreviamente: Boolean = false,
    val cuentaConTestigos: Boolean = false
)

class EvidenciaViewModel : ViewModel() {

    private val _estado = MutableStateFlow(EvidenciaState())
    val estado: StateFlow<EvidenciaState> = _estado

    fun cambiarEvidenciaExistente(valor: Boolean) {
        _estado.update { it.copy(evidenciaExistente = valor) }
    }

    fun cambiarOtrosAntecedentes(valor: Boolean) {
        _estado.update { it.copy(otrosAntecedentes = valor) }
    }

    fun cambiarInformadaPreviamente(valor: Boolean) {
        _estado.update { it.copy(informadaPreviamente = valor) }
    }

    fun cambiarCuentaConTestigos(valor: Boolean) {
        _estado.update { it.copy(cuentaConTestigos = valor) }
    }
}
