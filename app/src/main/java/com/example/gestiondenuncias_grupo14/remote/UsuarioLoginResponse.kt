// UsuarioLoginResponse.kt
package com.example.gestiondenuncias_grupo14.remote

/**
 * Response esperado del microservicio de autenticación.
 * Puede incluir un mensaje de éxito o datos del usuario.
 */
data class UsuarioLoginResponse(
    val mensaje: String
    // Si tu backend devuelve más datos (ej. Usuario), puedes agregarlos aquí.
)
