package com.example.gestiondenuncias_grupo14.data.local.dao

import androidx.room.*
import com.example.gestiondenuncias_grupo14.data.local.entity.FormularioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FormularioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarFormulario(formulario: FormularioEntity)

    @Query("SELECT * FROM formularios ORDER BY id DESC")
    fun obtenerFormularios(): Flow<List<FormularioEntity>>

    @Query("SELECT * FROM formularios WHERE id = :id")
    suspend fun obtenerPorId(id: Int): FormularioEntity?

    @Delete
    suspend fun eliminarFormulario(formulario: FormularioEntity)

    @Query("DELETE FROM formularios")
    suspend fun eliminarTodo()
}
