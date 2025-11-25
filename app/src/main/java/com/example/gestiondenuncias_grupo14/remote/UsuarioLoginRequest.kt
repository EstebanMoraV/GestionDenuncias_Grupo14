// UsuarioLoginRequest.kt
package com.example.gestiondenuncias_grupo14.remote

/**
 * Request para el endpoint de login.
 * Contiene las credenciales que el usuario ingresa en la aplicación.
 */
data class UsuarioLoginRequest(
    val correo: String,
    val contrasena: String
)
