package com.example.gestiondenuncias_grupo14.remote

data class UsuarioLoginResponse(
    val mensaje: String,
    val rut: String,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val empresa: String,
    val cargo: String,
    val depto: String
)
