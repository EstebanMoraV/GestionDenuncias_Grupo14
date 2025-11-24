package com.example.gestiondenuncias_grupo14.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.gestiondenuncias_grupo14.data.local.entity.FormularioEntity
import com.example.gestiondenuncias_grupo14.data.local.repository.FormularioRepository
import kotlinx.coroutines.launch

class FormularioDBViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FormularioRepository.getInstance(application)

    val formularios = repository.obtenerFormularios()

    // Guardar un formulario
    fun guardar(formulario: FormularioEntity) = viewModelScope.launch {
        repository.insertarFormulario(formulario)
    }

    // Eliminar un formulario específico
    fun eliminar(formulario: FormularioEntity) = viewModelScope.launch {
        repository.eliminarFormulario(formulario)
    }
}
