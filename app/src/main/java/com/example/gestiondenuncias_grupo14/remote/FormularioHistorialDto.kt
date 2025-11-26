package com.example.gestiondenuncias_grupo14.remote

data class FormularioHistorialDto(
    val id: Int,
    // Denunciado
    val denunciadoNombre: String,
    val denunciadoApellidoPaterno: String,
    val denunciadoApellidoMaterno: String,
    val denunciadoRut: String,
    val denunciadoCargo: String,
    val denunciadoArea: String,

    // Representante
    val representanteNombre: String,
    val representanteRut: String,

    // Denuncia
    val tiposSeleccionados: String,

    // Fecha
    val fechaCreacion: String
)
