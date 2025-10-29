package com.example.gestiondenuncias_grupo14.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gestiondenuncias_grupo14.model.Denunciado
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


class FormularioGlobalViewModel : ViewModel() {

    // 🔹 Estado global que contiene todos los datos
    private val _estado = MutableStateFlow(FormularioGlobalData())
    val estado: StateFlow<FormularioGlobalData> = _estado

    // --- GUARDAR PERSONAS ---
    fun guardarPersona(tipo: String, persona: Denunciado) {
        _estado.update {
            when (tipo.lowercase()) {
                "denunciado" -> it.copy(denunciado = persona)
                "representante" -> it.copy(representante = persona)
                "victima" -> it.copy(victima = persona)
                "testigo" -> it.copy(testigo = persona)
                else -> it
            }
        }
    }

    // --- GUARDAR DATOS DE TIPO DE DENUNCIA ---
    fun guardarTipoDenuncia(datos: TipoDenunciaData) {
        _estado.update { it.copy(tipoDenuncia = datos) }
    }

    // --- GUARDAR DATOS DE EVIDENCIA ---
    fun guardarEvidencia(datos: EvidenciaData) {
        _estado.update { it.copy(evidencia = datos) }
    }

    // --- GUARDAR RELATO ---
    fun guardarRelatoTexto(texto: String) {
        _estado.update { it.copy(relato = it.relato.copy(texto = texto)) }
    }

    fun guardarRelatoAudio(path: String) {
        _estado.update { it.copy(relato = it.relato.copy(audioPath = path)) }
    }

    // --- LIMPIAR FORMULARIO COMPLETO ---
    fun limpiarFormulario() {
        _estado.value = FormularioGlobalData()
    }
}

/**
 * 🔹 Data class principal que agrupa toda la información del formulario completo.
 */
data class FormularioGlobalData(
    val denunciado: Denunciado = Denunciado(),
    val representante: Denunciado = Denunciado(),
    val victima: Denunciado = Denunciado(),
    val testigo: Denunciado = Denunciado(),
    val tipoDenuncia: TipoDenunciaData = TipoDenunciaData(),
    val evidencia: EvidenciaData = EvidenciaData(),
    val relato: RelatoData = RelatoData()
)

/**
 * 🔹 Modelo para "Tipo de Denuncia"
 */
data class TipoDenunciaData(
    val tiposSeleccionados: List<String> = emptyList(),
    val relacionAsimetricaVictimaDepende: Boolean = false,
    val relacionAsimetricaDenunciadoDepende: Boolean = false,
    val relacionSimetricaMismaArea: Boolean = false,
    val relacionSimetricaDistintaArea: Boolean = false
)

/**
 * 🔹 Modelo para "Evidencia"
 */
data class EvidenciaData(
    val evidenciaExistente: Boolean = false,
    val otrosAntecedentes: Boolean = false,
    val informadaPreviamente: Boolean = false,
    val cuentaConTestigos: Boolean = false
)

/**
 * 🔹 Modelo para "Relato"
 */
data class RelatoData(
    val texto: String = "",
    val audioPath: String? = null
)



