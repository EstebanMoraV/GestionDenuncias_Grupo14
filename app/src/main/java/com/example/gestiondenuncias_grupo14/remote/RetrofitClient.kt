package com.example.gestiondenuncias_grupo14.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080" // Gateway

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            // Primero Scalars para respuestas tipo String
            .addConverterFactory(ScalarsConverterFactory.create())
            // Luego Gson para cuando uses JSON en otros endpoints
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
