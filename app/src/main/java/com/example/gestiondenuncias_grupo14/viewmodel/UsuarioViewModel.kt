package com.example.gestiondenuncias_grupo14.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gestiondenuncias_grupo14.model.Usuario

class UsuarioViewModel: ViewModel() {

    //Hacemos una lista
    private val usuarios = mutableListOf<Usuario>()
    var usuarioActual: Usuario? = null
        private set

    fun registrarUsuario(
        rut: String,
        nombre: String,
        apellido: String,
        correo: String,
        contrasena: String,
        empresa: String,
        cargo: String,
        depto: String
        ): Boolean{

        //Verificamos si existe
        if(usuarios.any{ it.rut == rut }) {
            return false
        }

        val nuevoUsuario = Usuario(rut, nombre, apellido, correo, contrasena, empresa, cargo, depto)
        usuarios.add(nuevoUsuario)
        usuarioActual = nuevoUsuario
        return true
    }

    fun login (correo: String, contrasena: String): Boolean{
        val usuario = usuarios.find { it.correo == correo && it.contrasena == contrasena }
        return if (usuario != null){
            usuarioActual = usuario
            true
        } else false
    }
}