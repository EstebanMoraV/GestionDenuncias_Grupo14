package com.example.gestiondenuncias_grupo14.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gestiondenuncias_grupo14.model.Usuario
import com.example.gestiondenuncias_grupo14.remote.RetrofitClient
import com.example.gestiondenuncias_grupo14.remote.UsuarioRequest
import com.example.gestiondenuncias_grupo14.remote.UsuarioLoginRequest
import com.example.gestiondenuncias_grupo14.remote.UsuarioLoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class UsuarioViewModel : ViewModel() {

    // -------------------------------------------------------------------------
    // Estado local
    // -------------------------------------------------------------------------

    private val usuarios = mutableListOf<Usuario>()

    var usuarioActual: Usuario? = null
        private set

    var fotoUri: Uri? by mutableStateOf(null)

    // -------------------------------------------------------------------------
    // Datos estáticos
    // -------------------------------------------------------------------------

    val cargos = listOf("Operario", "Técnico", "Analista", "Ingeniero", "Arquitecto", "Diseñador",
        "Programador", "Desarrollador", "Tester", "Supervisor", "Coordinador", "Gerente de Proyectos",
        "Jefe de Área", "Gerente", "Director", "Secretario", "Contador", "Auditor", "Vendedor",
        "Minero", "Bombero", "Carabinero", "Otros")

    val dptos = listOf("RRHH", "Producción", "Logística", "Marketing", "Finanzas",
        "Comercial", "Legal", "Otros")

    // -------------------------------------------------------------------------
    // Funciones locales
    // -------------------------------------------------------------------------

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
        if (usuarios.any { it.rut == rut }) return false
        val nuevoUsuario = Usuario(rut, nombre, apellido, correo, contrasena, empresa, cargo, depto)
        usuarios.add(nuevoUsuario)
        usuarioActual = nuevoUsuario
        return true
    }

    fun login(correo: String, contrasena: String): Boolean {
        val usuario = usuarios.find { it.correo == correo && it.contrasena == contrasena }
        return if (usuario != null) {
            usuarioActual = usuario
            true
        } else false
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
    // Integración remota con microservicio
    // -------------------------------------------------------------------------

    private val _resultado = MutableLiveData<String?>()
    val resultado: LiveData<String?> get() = _resultado

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

    // -------------------------------------------------------------------------
    // Login remoto con datos reales
    // -------------------------------------------------------------------------

    private val _loginResultado = MutableLiveData<String?>()
    val loginResultado: LiveData<String?> get() = _loginResultado

    fun loginRemoto(correo: String, contrasena: String) {
        val request = UsuarioLoginRequest(correo, contrasena)

        RetrofitClient.apiService.login(request)
            .enqueue(object : Callback<UsuarioLoginResponse> {
                override fun onResponse(
                    call: Call<UsuarioLoginResponse>,
                    response: Response<UsuarioLoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null && body.mensaje.contains("exitoso", ignoreCase = true)) {
                            usuarioActual = Usuario(
                                rut = body.rut,
                                nombre = body.nombre,
                                apellido = body.apellido,
                                correo = body.correo,
                                contrasena = contrasena,
                                empresa = body.empresa,
                                cargo = body.cargo,
                                depto = body.depto
                            )
                        }
                        _loginResultado.postValue(body?.mensaje ?: "Respuesta vacía")
                    } else {
                        _loginResultado.postValue("Error en login: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<UsuarioLoginResponse>, t: Throwable) {
                    _loginResultado.postValue("Fallo en la conexión: ${t.message}")
                }
            })
    }

    fun resetLoginResultado() {
        _loginResultado.value = null
    }

    fun resetResultado() {
        _resultado.value = null
    }
}
