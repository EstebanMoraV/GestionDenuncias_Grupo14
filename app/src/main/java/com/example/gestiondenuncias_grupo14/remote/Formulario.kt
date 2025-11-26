package com.example.gestiondenuncias_grupo14.remote

data class Formulario(
    val id: Int? = null,

    // ── Denunciado ──
    val denunciadoNombre: String,
    val denunciadoApellidoPaterno: String,
    val denunciadoApellidoMaterno: String,
    val denunciadoRut: String,
    val denunciadoCargo: String,
    val denunciadoArea: String,

    // ── Representante ──
    val representanteNombre: String,
    val representanteRut: String,

    // ── Tipos de denuncia (resumen en texto) ──
    val tiposSeleccionados: String,

    // ── Evidencia ──
    val evidenciaExistente: Boolean,
    val otrosAntecedentes: Boolean,
    val informadaPreviamente: Boolean,
    val cuentaConTestigos: Boolean,

    // ── Relación víctima / denunciado ──
    val relacionAsimetricaVictimaDepende: Boolean,
    val relacionAsimetricaDenunciadoDepende: Boolean,
    val relacionSimetricaMismaArea: Boolean,
    val relacionSimetricaDistintaArea: Boolean,

    // ── Relato ──
    val relatoTexto: String? = null,
    val relatoAudio: String? = null,

    // ── Auditoría ──
    val fechaCreacion: String? = null
)
