package com.example.gestiondenuncias_grupo14.viewmodel

import com.example.gestiondenuncias_grupo14.remote.Formulario

data class FormularioGlobalData(

    // ---- DENUNCIADO ----
    val denunciadoNombre: String = "",
    val denunciadoApellidoPaterno: String = "",
    val denunciadoApellidoMaterno: String = "",
    val denunciadoRut: String = "",
    val denunciadoCargo: String = "",
    val denunciadoArea: String = "",

    // ---- REPRESENTANTE ----
    val representanteNombre: String = "",
    val representanteRut: String = "",

    // ---- TIPOS ----
    val tiposSeleccionados: String = "",

    // ---- EVIDENCIA ----
    val evidenciaExistente: Boolean = false,
    val otrosAntecedentes: Boolean = false,
    val informadaPreviamente: Boolean = false,
    val cuentaConTestigos: Boolean = false,

    // ---- RELACIÓN ----
    val relacionAsimetricaVictimaDepende: Boolean = false,
    val relacionAsimetricaDenunciadoDepende: Boolean = false,
    val relacionSimetricaMismaArea: Boolean = false,
    val relacionSimetricaDistintaArea: Boolean = false,

    // ---- RELATO ----
    val relatoTexto: String = "",
    val relatoAudio: String = ""
) {

    fun toFormulario(): Formulario = Formulario(
        id = null,

        // Denunciado
        denunciadoNombre = denunciadoNombre,
        denunciadoApellidoPaterno = denunciadoApellidoPaterno,
        denunciadoApellidoMaterno = denunciadoApellidoMaterno,
        denunciadoRut = denunciadoRut,
        denunciadoCargo = denunciadoCargo,
        denunciadoArea = denunciadoArea,

        // Representante
        representanteNombre = representanteNombre,
        representanteRut = representanteRut,

        // Tipos
        tiposSeleccionados = tiposSeleccionados,

        // Evidencia
        evidenciaExistente = evidenciaExistente,
        otrosAntecedentes = otrosAntecedentes,
        informadaPreviamente = informadaPreviamente,
        cuentaConTestigos = cuentaConTestigos,

        // Relación
        relacionAsimetricaVictimaDepende = relacionAsimetricaVictimaDepende,
        relacionAsimetricaDenunciadoDepende = relacionAsimetricaDenunciadoDepende,
        relacionSimetricaMismaArea = relacionSimetricaMismaArea,
        relacionSimetricaDistintaArea = relacionSimetricaDistintaArea,

        // Relato
        relatoTexto = if (relatoTexto.isBlank()) null else relatoTexto,
        relatoAudio = if (relatoAudio.isBlank()) null else relatoAudio,

        fechaCreacion = null
    )
}
