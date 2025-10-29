package com.example.gestiondenuncias_grupo14.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TipoDenunciaViewModel : ViewModel() {
    private val _estado = MutableStateFlow(TipoDenunciaData())
    val estado: StateFlow<TipoDenunciaData> = _estado

    fun toggleTipo(tipo: String) {
        _estado.update { current ->
            val nuevaLista = if (tipo in current.tiposSeleccionados)
                current.tiposSeleccionados - tipo
            else
                current.tiposSeleccionados + tipo
            current.copy(tiposSeleccionados = nuevaLista)
        }
    }

    fun cambiarRelacionAsimetricaVictima(valor: Boolean) {
        _estado.update { it.copy(relacionAsimetricaVictimaDepende = valor) }
    }

    fun cambiarRelacionAsimetricaDenunciado(valor: Boolean) {
        _estado.update { it.copy(relacionAsimetricaDenunciadoDepende = valor) }
    }

    fun cambiarRelacionSimetricaMismaArea(valor: Boolean) {
        _estado.update { it.copy(relacionSimetricaMismaArea = valor) }
    }

    fun cambiarRelacionSimetricaDistintaArea(valor: Boolean) {
        _estado.update { it.copy(relacionSimetricaDistintaArea = valor) }
    }
}


