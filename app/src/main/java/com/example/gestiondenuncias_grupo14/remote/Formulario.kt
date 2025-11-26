package com.example.gestiondenuncias_grupo14.remote

data class Formulario(
    val id: Int? = null,

    // DENUNCIADO
    val denunciadoNombre: String,
    val denunciadoApellidoPaterno: String,
    val denunciadoApellidoMaterno: String?,
    val denunciadoRut: String,
    val denunciadoCargo: String?,
    val denunciadoArea: String?,

    // REPRESENTANTE
    val representanteNombre: String?,
    val representanteApellidoPaterno: String?,
    val representanteApellidoMaterno: String?,
    val representanteRut: String?,
    val representanteCargo: String?,
    val representanteArea: String?,

    // VÍCTIMA
    val victimaNombre: String?,
    val victimaApellidoPaterno: String?,
    val victimaApellidoMaterno: String?,
    val victimaRut: String?,
    val victimaCargo: String?,
    val victimaArea: String?,

    // TESTIGO
    val testigoNombre: String?,
    val testigoApellidoPaterno: String?,
    val testigoApellidoMaterno: String?,
    val testigoRut: String?,
    val testigoCargo: String?,
    val testigoArea: String?,

    // TIPO DE DENUNCIA
    val tiposSeleccionados: String,
    val relacionAsimetricaVictimaDepende: Boolean?,
    val relacionAsimetricaDenunciadoDepende: Boolean?,
    val relacionSimetricaMismaArea: Boolean?,
    val relacionSimetricaDistintaArea: Boolean?,

    // EVIDENCIAS
    val evidenciaExistente: Boolean?,
    val otrosAntecedentes: Boolean?,
    val informadaPreviamente: Boolean?,
    val cuentaConTestigos: Boolean?,

    // RELATO
    val relatoTexto: String?,
    val relatoAudioPath: String?,

    // METADATOS
    val fechaCreacion: String? = null
)

