package com.example.gestiondenuncias_grupo14.remote

import android.R.attr.level
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    // PRODUCCIÓN (Railway - API Gateway)
    private const val BASE_URL = "https://apigateway-production-f02f.up.railway.app/"

    /*
    // probar local con el emulador:
    private const val BASE_URL = "http://10.0.2.2:8080/"
    */

    // Cliente HTTP con logs y timeouts razonables
    private val okHttpClient: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Quita o baja a BASIC en producción
        }

        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            // Primero Scalars para respuestas tipo String
            .addConverterFactory(ScalarsConverterFactory.create())
            // Luego Gson para cuando uses JSON
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

