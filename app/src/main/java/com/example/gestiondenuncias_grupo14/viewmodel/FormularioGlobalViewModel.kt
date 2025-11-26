package com.example.gestiondenuncias_grupo14.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestiondenuncias_grupo14.model.Denunciado
import com.example.gestiondenuncias_grupo14.remote.Formulario
import com.example.gestiondenuncias_grupo14.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

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

    // --- CONSTRUIR OBJETO FORMULARIO PARA EL BACKEND ---
    private fun construirFormulario(): Formulario {
        val estadoActual = _estado.value

        return Formulario(
            id = null,

            // DENUNCIADO
            denunciadoNombre = estadoActual.denunciado.nombre,
            denunciadoApellidoPaterno = estadoActual.denunciado.apellido_paterno,
            denunciadoApellidoMaterno = estadoActual.denunciado.apellido_materno,
            denunciadoRut = estadoActual.denunciado.rut,
            denunciadoCargo = estadoActual.denunciado.cargo,
            denunciadoArea = estadoActual.denunciado.dpto_gcia_area,

            // REPRESENTANTE
            representanteNombre = estadoActual.representante.nombre,
            representanteApellidoPaterno = estadoActual.representante.apellido_paterno,
            representanteApellidoMaterno = estadoActual.representante.apellido_materno,
            representanteRut = estadoActual.representante.rut,
            representanteCargo = estadoActual.representante.cargo,
            representanteArea = estadoActual.representante.dpto_gcia_area,

            // VÍCTIMA
            victimaNombre = estadoActual.victima.nombre,
            victimaApellidoPaterno = estadoActual.victima.apellido_paterno,
            victimaApellidoMaterno = estadoActual.victima.apellido_materno,
            victimaRut = estadoActual.victima.rut,
            victimaCargo = estadoActual.victima.cargo,
            victimaArea = estadoActual.victima.dpto_gcia_area,

            // TESTIGO
            testigoNombre = estadoActual.testigo.nombre,
            testigoApellidoPaterno = estadoActual.testigo.apellido_paterno,
            testigoApellidoMaterno = estadoActual.testigo.apellido_materno,
            testigoRut = estadoActual.testigo.rut,
            testigoCargo = estadoActual.testigo.cargo,
            testigoArea = estadoActual.testigo.dpto_gcia_area,

            // TIPO DE DENUNCIA
            tiposSeleccionados = estadoActual.tipoDenuncia.tiposSeleccionados.joinToString(","),
            relacionAsimetricaVictimaDepende = estadoActual.tipoDenuncia.relacionAsimetricaVictimaDepende,
            relacionAsimetricaDenunciadoDepende = estadoActual.tipoDenuncia.relacionAsimetricaDenunciadoDepende,
            relacionSimetricaMismaArea = estadoActual.tipoDenuncia.relacionSimetricaMismaArea,
            relacionSimetricaDistintaArea = estadoActual.tipoDenuncia.relacionSimetricaDistintaArea,

            // EVIDENCIAS
            evidenciaExistente = estadoActual.evidencia.evidenciaExistente,
            otrosAntecedentes = estadoActual.evidencia.otrosAntecedentes,
            informadaPreviamente = estadoActual.evidencia.informadaPreviamente,
            cuentaConTestigos = estadoActual.evidencia.cuentaConTestigos,

            // RELATO
            relatoTexto = estadoActual.relato.texto,
            relatoAudioPath = estadoActual.relato.audioPath,

            // METADATOS
            fechaCreacion = null // lo genera el backend
        )
    }

    // --- ENVIAR FORMULARIO AL BACKEND ---
    fun enviarFormulario(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val formulario = construirFormulario()
                val response = RetrofitClient.apiService.createFormulario(formulario).awaitResponse()

                if (response.isSuccessful) {
                    Log.d("Formulario", "Formulario enviado correctamente: ${response.body()}")
                    onResult(true)
                } else {
                    val errorMsg = response.errorBody()?.string()
                    Log.e("Formulario", "Error al enviar: Código ${response.code()} - $errorMsg")
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("Formulario", "Excepción: ${e.localizedMessage}", e)
                onResult(false)
            }
        }
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
