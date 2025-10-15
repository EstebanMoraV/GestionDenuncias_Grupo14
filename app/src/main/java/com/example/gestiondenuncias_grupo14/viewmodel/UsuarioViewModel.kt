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
    fun validarCampos(
        rut: String,
        nombre: String,
        apellido: String,
        correo: String,
        contrasena: String,
        empresa: String,
        cargo: String,
        depArea: String
    ): String? {
        if (rut.isBlank()) return "El campo Rut es obligatorio"
        if (!Regex("""^\d{7,8}-[0-9kK]$""").matches(rut)) return "El Rut no tiene el formato correcto"
        if (nombre.isBlank()) return "El nombre es obligatorio"
        if (apellido.isBlank()) return "El apellido es obligatorio"
        if (correo.isBlank()) return "El correo es obligatorio"
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) return "El correo no es válido"
        if (contrasena.length < 6) return "La contraseña debe tener al menos 6 caracteres"
        if (empresa.isBlank()) return "Debe seleccionar una empresa"
        if (cargo.isBlank()) return "El cargo es obligatorio"
        if (depArea.isBlank()) return "El área o departamento es obligatorio"
        return null // Todo está bien
    }

}