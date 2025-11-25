package com.example.gestiondenuncias_grupo14.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gestiondenuncias_grupo14.model.Usuario
import com.example.gestiondenuncias_grupo14.remote.RetrofitClient
import com.example.gestiondenuncias_grupo14.remote.UsuarioRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.gestiondenuncias_grupo14.remote.UsuarioLoginRequest
import com.example.gestiondenuncias_grupo14.remote.UsuarioLoginResponse

/**
 * ViewModel responsable de la gestión de usuarios.
 * Contiene lógica local (cache en memoria, validaciones) y lógica remota (integración con microservicios vía Retrofit).
 */
class UsuarioViewModel : ViewModel() {

    // -------------------------------------------------------------------------
    // Estado local
    // -------------------------------------------------------------------------

    /** Lista de usuarios mantenida en memoria como cache temporal. */
    private val usuarios = mutableListOf<Usuario>()

    /** Usuario actualmente autenticado en la aplicación. */
    var usuarioActual: Usuario? = null
        private set

    /** URI de la foto seleccionada por el usuario (ejemplo: desde galería). */
    var fotoUri: Uri? by mutableStateOf(null)

    // -------------------------------------------------------------------------
    // Datos estáticos para formularios
    // -------------------------------------------------------------------------

    /** Lista de cargos disponibles para selección en formularios. */
    val cargos = listOf(
        "Operario", "Técnico", "Analista", "Ingeniero", "Arquitecto", "Diseñador", "Programador",
        "Desarrollador", "Tester", "Supervisor", "Coordinador", "Gerente de Proyectos",
        "Jefe de Área", "Gerente", "Director", "Secretario", "Contador", "Auditor", "Vendedor",
        "Minero", "Bombero", "Carabinero", "Otros"
    )

    /** Lista de departamentos disponibles para selección en formularios. */
    val dptos = listOf(
        "RRHH", "Producción", "Logística", "Marketing", "Finanzas",
        "Comercial", "Legal", "Otros"
    )

    // -------------------------------------------------------------------------
    // Funciones locales (sin conexión a servidor)
    // -------------------------------------------------------------------------

    /**
     * Registra un usuario en memoria local.
     * @return true si el usuario fue registrado, false si ya existía un usuario con el mismo rut.
     */
    fun registrarUsuario(
        rut: String,
        nombre: String,
        apellido: String,
        correo: String,
        contrasena: String,
        empresa: String,
        cargo: String,
        depto: String
    ): Boolean {
        if (usuarios.any { it.rut == rut }) {
            return false
        }
        val nuevoUsuario = Usuario(rut, nombre, apellido, correo, contrasena, empresa, cargo, depto)
        usuarios.add(nuevoUsuario)
        usuarioActual = nuevoUsuario
        return true
    }

    /**
     * Realiza login contra la lista local de usuarios.
     * @return true si las credenciales coinciden, false en caso contrario.
     */
    fun login(correo: String, contrasena: String): Boolean {
        val usuario = usuarios.find { it.correo == correo && it.contrasena == contrasena }
        return if (usuario != null) {
            usuarioActual = usuario
            true
        } else {
            false
        }
    }

    /**
     * Valida los campos de entrada antes de registrar un usuario.
     * @return mensaje de error si algún campo es inválido, o null si todos son correctos.
     */
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
        return null
    }

    // -------------------------------------------------------------------------
    // Integración remota con microservicio (Retrofit)
    // -------------------------------------------------------------------------

    /** LiveData para observar el resultado de la operación remota de creación de usuario. */
    private val _resultado = MutableLiveData<String?>()
    val resultado: LiveData<String?> get() = _resultado

    /**
     * Registra un usuario en el microservicio remoto utilizando Retrofit.
     * Actualiza el estado local en caso de éxito y publica el resultado en LiveData.
     */
    fun registrarUsuarioRemoto(
        rut: String,
        nombre: String,
        apellido: String,
        correo: String,
        contrasena: String,
        empresa: String,
        cargo: String,
        depto: String
    ) {
        val request = UsuarioRequest(rut, nombre, apellido, correo, contrasena, empresa, cargo, depto)

        RetrofitClient.apiService.crearUsuario(request)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        val nuevoUsuario = Usuario(rut, nombre, apellido, correo, contrasena, empresa, cargo, depto)
                        usuarios.add(nuevoUsuario)
                        usuarioActual = nuevoUsuario
                        _resultado.postValue("Usuario creado exitosamente en el servidor")
                    } else {
                        _resultado.postValue("Error al crear usuario: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    _resultado.postValue("Fallo en la conexión: ${t.message}")
                }
            })
    }


    // Dentro de UsuarioViewModel

    /** LiveData para observar el resultado del login remoto. */
    private val _loginResultado = MutableLiveData<String?>()
    val loginResultado: LiveData<String?> get() = _loginResultado

    /**
     * Realiza login contra el microservicio de autenticación.
     * Envía correo y contraseña, y publica el resultado en LiveData.
     */
    fun loginRemoto(correo: String, contrasena: String) {
        val request = UsuarioLoginRequest(correo, contrasena)

        RetrofitClient.apiService.login(request)
            .enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        // El backend devuelve directamente un String
                        _loginResultado.postValue(response.body() ?: "Respuesta vacía")
                    } else {
                        _loginResultado.postValue("Error en login: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    _loginResultado.postValue("Fallo en la conexión: ${t.message}")
                }
            })
    }


    /**
     * Limpia el resultado del login después de que la UI lo consuma.
     * Esto evita que el mensaje se repita al volver a la pantalla.
     */
    fun resetLoginResultado() {
        _loginResultado.value = null
    }


    /**
     * Limpia el valor de resultado después de que la UI lo haya consumido.
     * Esto evita que el mensaje se repita al volver a la pantalla.
     */
    fun resetResultado() {
        _resultado.value = null
    }
}
