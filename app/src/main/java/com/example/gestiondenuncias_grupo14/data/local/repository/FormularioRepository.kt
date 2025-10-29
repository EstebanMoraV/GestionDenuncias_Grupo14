package com.example.gestiondenuncias_grupo14.data.local.repository

import android.content.Context
import androidx.room.Room
import com.example.gestiondenuncias_grupo14.data.local.database.AppDatabase
import com.example.gestiondenuncias_grupo14.data.local.entity.FormularioEntity
import kotlinx.coroutines.flow.Flow

class FormularioRepository private constructor(context: Context) {

    private val db = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "formularios_db"
    ).build()

    private val formularioDao = db.formularioDao()

    fun obtenerFormularios(): Flow<List<FormularioEntity>> = formularioDao.obtenerFormularios()

    suspend fun insertarFormulario(formulario: FormularioEntity) {
        formularioDao.insertarFormulario(formulario)
    }

    suspend fun eliminarFormulario(formulario: FormularioEntity) {
        formularioDao.eliminarFormulario(formulario)
    }

    suspend fun eliminarTodo() {
        formularioDao.eliminarTodo()
    }

    companion object {
        @Volatile private var INSTANCE: FormularioRepository? = null

        fun getInstance(context: Context): FormularioRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FormularioRepository(context).also { INSTANCE = it }
            }
    }
}
