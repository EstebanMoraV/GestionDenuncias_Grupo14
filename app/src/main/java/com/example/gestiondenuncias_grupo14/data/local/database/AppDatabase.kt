package com.example.gestiondenuncias_grupo14.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gestiondenuncias_grupo14.data.local.dao.FormularioDao
import com.example.gestiondenuncias_grupo14.data.local.entity.FormularioEntity

@Database(
    entities = [FormularioEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun formularioDao(): FormularioDao
}
