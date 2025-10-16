package com.example.gestiondenuncias_grupo14.model

data class Representante(
    val nombreRep: String = "",
    val apellido_paterno: String = "",
    val apellido_materno: String = "",
    val rut: String = "",
    val cargo: String = "",
    val dpto_gcia_area: String = "",
    val errores: RepresentanteError = RepresentanteError() // Objeto que contiene los errores por campos
)