package com.example.gestiondenuncias_grupo14.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gestiondenuncias_grupo14.model.Denunciado
import com.example.gestiondenuncias_grupo14.model.DenunciadoError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class FormularioPersonaViewModel : ViewModel() {

    private val _estado = MutableStateFlow(value = Denunciado())
    val estado: StateFlow<Denunciado> = _estado

    val cargos = listOf(
        "Operario", "Técnico", "Analista", "Ingeniero", "Gerente", "Supervisor",
        "Programador", "Diseñador", "Otros"
    )

    val dptos = listOf(
        "RRHH", "Producción", "Comercial", "Marketing", "Legal", "Finanzas", "Otros"
    )

    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    fun onApellidoPaternoChange(valor: String) {
        _estado.update { it.copy(apellido_paterno = valor, errores = it.errores.copy(apellido_paterno = null)) }
    }

    fun onApellidoMaternoChange(valor: String) {
        _estado.update { it.copy(apellido_materno = valor, errores = it.errores.copy(apellido_materno = null)) }
    }

    fun onRutChange(valor: String) {
        _estado.update { it.copy(rut = valor, errores = it.errores.copy(rut = null)) }
    }

    fun onCargoChange(valor: String) {
        _estado.update { it.copy(cargo = valor, errores = it.errores.copy(cargo = null)) }
    }

    fun onDptoGciaAreaChange(valor: String) {
        _estado.update { it.copy(dpto_gcia_area = valor, errores = it.errores.copy(dpto_gcia_area = null)) }
    }

    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        val errores = DenunciadoError(
            nombre = if (estadoActual.nombre.isBlank()) "El nombre es obligatorio" else null,
            apellido_paterno = if (estadoActual.apellido_paterno.isBlank()) "El apellido paterno es obligatorio" else null,
            apellido_materno = if (estadoActual.apellido_materno.isBlank()) "El apellido materno es obligatorio" else null,
            rut = if (estadoActual.rut.isBlank()) "El RUT es obligatorio" else null,
            cargo = if (estadoActual.cargo.isBlank()) "Debe seleccionar un cargo" else null,
            dpto_gcia_area = if (estadoActual.dpto_gcia_area.isBlank()) "Debe seleccionar un departamento o área" else null
        )

        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.apellido_paterno,
            errores.apellido_materno,
            errores.rut,
            errores.cargo,
            errores.dpto_gcia_area
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores
    }
}

