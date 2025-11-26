package com.example.gestiondenuncias_grupo14.remote

import FormularioHistorialDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Path

interface ApiService {

    // Endpoint para crear usuario
    @POST("/api/usuarios")
    fun crearUsuario(@Body usuario: UsuarioRequest): Call<Void>

    /**
     * Endpoint para autenticar un usuario en el sistema.
     * Recibe correo y contraseña, devuelve un mensaje de éxito o error.
     */
    @POST("/api/auth/login")
    fun login(@Body request: UsuarioLoginRequest): Call<UsuarioLoginResponse>


    // ---------------- NUEVOS ENDPOINTS: FORMULARIOS ----------------

    @GET("/formularios")
    fun getAllFormularios(): Call<List<Formulario>>

    @GET("/formularios/{id}")
    fun getFormularioById(@Path("id") id: Int): Call<Formulario>

    @POST("/formularios")
    fun createFormulario(@Body formulario: Formulario): Call<Formulario>

    @PUT("/formularios/{id}")
    fun updateFormulario(@Path("id") id: Int, @Body formulario: Formulario): Call<Formulario>

    @DELETE("/formularios/{id}")
    fun deleteFormulario(@Path("id") id: Int): Call<Void>


    // -------End Points del historial
    @GET("/historial")
    suspend fun getHistorial(): List<FormularioHistorialDto>

    @DELETE("/historial/{id}")
    suspend fun deleteHistorial(@Path("id") id: Int): retrofit2.Response<Void>
}
