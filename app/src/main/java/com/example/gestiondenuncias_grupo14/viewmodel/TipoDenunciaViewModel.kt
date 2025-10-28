package com.example.gestiondenuncias_grupo14.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class TipoDenunciaState(
    val tiposSeleccionados: List<String> = emptyList(),
    val relacionAsimetricaVictimaDepende: Boolean = false,
    val relacionAsimetricaDenunciadoDepende: Boolean = false,
    val relacionSimetricaMismaArea: Boolean = false,
    val relacionSimetricaDistintaArea: Boolean = false
)

class TipoDenunciaViewModel : ViewModel() {

    private val _estado = MutableStateFlow(TipoDenunciaState())
    val estado: StateFlow<TipoDenunciaState> = _estado

    // Alternar selección de tipo de denuncia
    fun toggleTipo(tipo: String) {
        _estado.update { actual ->
            val nuevaLista = if (tipo in actual.tiposSeleccionados) {
                actual.tiposSeleccionados - tipo
            } else {
                actual.tiposSeleccionados + tipo
            }
            actual.copy(tiposSeleccionados = nuevaLista)
        }
    }

    // Switch: la víctima depende del denunciado
    fun cambiarRelacionAsimetricaVictima(valor: Boolean) {
        _estado.update { it.copy(relacionAsimetricaVictimaDepende = valor) }
    }

    // Switch: el denunciado depende de la víctima
    fun cambiarRelacionAsimetricaDenunciado(valor: Boolean) {
        _estado.update { it.copy(relacionAsimetricaDenunciadoDepende = valor) }
    }

    // Switch: relación simétrica misma área
    fun cambiarRelacionSimetricaMismaArea(valor: Boolean) {
        _estado.update { it.copy(relacionSimetricaMismaArea = valor) }
    }

    // Switch: relación simétrica distinta área
    fun cambiarRelacionSimetricaDistintaArea(valor: Boolean) {
        _estado.update { it.copy(relacionSimetricaDistintaArea = valor) }
    }
}

