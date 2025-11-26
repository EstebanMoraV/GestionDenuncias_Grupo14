// remote/UsuarioRequest.kt
package com.example.gestiondenuncias_grupo14.remote

data class UsuarioRequest(
    val rut: String,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val contrasena: String,
    val empresa: String,
    val cargo: String,
    val depto: String
)
