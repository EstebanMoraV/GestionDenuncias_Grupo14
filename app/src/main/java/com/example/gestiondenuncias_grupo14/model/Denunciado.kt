package com.example.gestiondenuncias_grupo14.model

data class Denunciado(
    val nombre: String = "",
    val apellido_paterno: String = "",
    val apellido_materno: String = "",
    val rut: String = "",
    val cargo: String = "",
    val dpto_gcia_area: String = "",
    val errores: DenunciadoError = DenunciadoError() // Objeto que contiene los errores por campos
)