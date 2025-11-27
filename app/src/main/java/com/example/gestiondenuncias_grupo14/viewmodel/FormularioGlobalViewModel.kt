package com.example.gestiondenuncias_grupo14.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestiondenuncias_grupo14.model.Denunciado
import com.example.gestiondenuncias_grupo14.remote.FormularioHistorialDto
import com.example.gestiondenuncias_grupo14.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FormularioGlobalViewModel : ViewModel() {

    // ---------------- ESTADO GLOBAL DEL FORMULARIO ----------------
    private val _estado = MutableStateFlow(FormularioGlobalData())
    val estado: StateFlow<FormularioGlobalData> = _estado

    // ---------------- HISTORIAL DESDE BACKEND ----------------
    private val _historial = MutableStateFlow<List<FormularioHistorialDto>>(emptyList())
    val historial: StateFlow<List<FormularioHistorialDto>> = _historial

    // Copias internas opcionales
    private var evidenciaData: EvidenciaData? = null
    private var tipoDenunciaData: TipoDenunciaData? = null

    // -------------------------------------------------------------
    // GUARDAR PERSONA (DENUNCIADO / REPRESENTANTE)
    // -------------------------------------------------------------
    fun guardarPersona(tipo: String, persona: Denunciado) {
        when (tipo) {
            "denunciado" -> {
                _estado.update {
                    it.copy(
                        denunciadoNombre = persona.nombre,
                        denunciadoApellidoPaterno = persona.apellido_paterno,
                        denunciadoApellidoMaterno = persona.apellido_materno,
                        denunciadoRut = persona.rut,
                        denunciadoCargo = persona.cargo,
                        denunciadoArea = persona.dpto_gcia_area
                    )
                }
            }

            "representante" -> {
                _estado.update {
                    it.copy(
                        representanteNombre = persona.nombre,
                        representanteRut = persona.rut
                    )
                }
            }
        }
    }

    // -------------------------------------------------------------
    // EVIDENCIA
    // -------------------------------------------------------------
    fun guardarEvidencia(datos: EvidenciaData) {
        evidenciaData = datos
        _estado.update {
            it.copy(
                evidenciaExistente = datos.evidenciaExistente,
                otrosAntecedentes = datos.otrosAntecedentes,
                informadaPreviamente = datos.informadaPreviamente,
                cuentaConTestigos = datos.cuentaConTestigos
            )
        }
    }

    // -------------------------------------------------------------
    // TIPO DE DENUNCIA
    // -------------------------------------------------------------
    fun guardarTipoDenuncia(datos: TipoDenunciaData) {
        tipoDenunciaData = datos

        // Resumen legible de tipos seleccionados
        val resumenTipos = datos.tiposSeleccionados.joinToString(separator = " · ")

        _estado.update {
            it.copy(
                tiposSeleccionados = resumenTipos,
                relacionAsimetricaVictimaDepende = datos.relacionAsimetricaVictimaDepende,
                relacionAsimetricaDenunciadoDepende = datos.relacionAsimetricaDenunciadoDepende,
                relacionSimetricaMismaArea = datos.relacionSimetricaMismaArea,
                relacionSimetricaDistintaArea = datos.relacionSimetricaDistintaArea
            )
        }
    }

    // -------------------------------------------------------------
    // RELATO
    // -------------------------------------------------------------
    fun guardarRelatoTexto(texto: String) {
        _estado.update { it.copy(relatoTexto = texto) }
    }

    fun guardarRelatoAudio(audio: String) {
        _estado.update { it.copy(relatoAudio = audio) }
    }

    // -------------------------------------------------------------
    // HISTORIAL: CARGAR LISTA DESDE /historial
    // -------------------------------------------------------------
    fun cargarHistorial(
        onError: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                val lista = RetrofitClient.apiService.getHistorial()
                _historial.value = lista
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Error al cargar historial")
            }
        }
    }

    // -------------------------------------------------------------
    // HISTORIAL: ELIMINAR UN FORMULARIO
    // -------------------------------------------------------------
    fun eliminarDelHistorial(
        id: Int,
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.deleteHistorial(id)
                if (response.isSuccessful) {
                    onSuccess()
                    // recargar lista actualizada
                    cargarHistorial()
                } else {
                    onError("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Error al eliminar")
            }
        }
    }

    // -------------------------------------------------------------
    // ENVIAR FORMULARIO AL BACKEND
    // -------------------------------------------------------------
    fun createFormulario(
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                val formulario = _estado.value.toFormulario()

                val response = RetrofitClient.apiService.crearFormulario(formulario)

                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Error ${response.code()}: ${response.message()}")
                }

            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Error desconocido")
            }
        }
    }

    // -------------------------------------------------------------
    // LIMPIAR FORMULARIO
    // -------------------------------------------------------------
    fun limpiarFormulario() {
        evidenciaData = null
        tipoDenunciaData = null
        _estado.value = FormularioGlobalData()
    }
}

