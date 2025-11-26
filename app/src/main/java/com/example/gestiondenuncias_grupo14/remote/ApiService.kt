package com.example.gestiondenuncias_grupo14.remote

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

import com.example.gestiondenuncias_grupo14.remote.UsuarioLoginRequest
import com.example.gestiondenuncias_grupo14.remote.UsuarioLoginResponse
import com.example.gestiondenuncias_grupo14.remote.UsuarioRequest
import com.example.gestiondenuncias_grupo14.remote.Formulario
import com.example.gestiondenuncias_grupo14.remote.FormularioHistorialDto

interface ApiService {

    // ---------- LOGIN ----------
    @POST("/api/auth/login")
    fun login(@Body request: UsuarioLoginRequest): Call<UsuarioLoginResponse>

    // ---------- USUARIOS ----------
    @POST("/api/usuarios")
    fun crearUsuario(@Body usuario: UsuarioRequest): Call<Void>

    // ---------- FORMULARIOS ----------
    @POST("/formularios")
    suspend fun crearFormulario(
        @Body formulario: Formulario
    ): Response<String>

    @GET("/formularios")
    fun getFormularios(): Call<List<Formulario>>

    @GET("/formularios/{id}")
    fun getFormulario(@Path("id") id: Int): Call<Formulario>

    @PUT("/formularios/{id}")
    fun updateFormulario(
        @Path("id") id: Int,
        @Body formulario: Formulario
    ): Call<Formulario>

    @DELETE("/formularios/{id}")
    fun deleteFormulario(@Path("id") id: Int): Call<Void>

    // ---------- HISTORIAL ----------
    @GET("/historial")
    suspend fun getHistorial(): List<FormularioHistorialDto>

    @DELETE("/historial/{id}")
    suspend fun deleteHistorial(@Path("id") id: Int): Response<Void>
}

