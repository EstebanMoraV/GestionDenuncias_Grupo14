package com.example.gestiondenuncias_grupo14.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    // Endpoint para crear usuario
    @POST("/api/usuarios")
    fun crearUsuario(@Body usuario: UsuarioRequest): Call<Void>

    /**
     * Endpoint para autenticar un usuario en el sistema.
     * Recibe correo y contraseña, devuelve un mensaje de éxito o error.
     */
    @POST("/api/auth/login")
    fun login(@Body request: UsuarioLoginRequest): Call<String>
}
