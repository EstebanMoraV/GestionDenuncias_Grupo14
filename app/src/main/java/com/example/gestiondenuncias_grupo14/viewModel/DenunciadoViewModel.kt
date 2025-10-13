package com.example.gestiondenuncias_grupo14.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gestiondenuncias_grupo14.model.Denunciado
import com.example.gestiondenuncias_grupo14.model.DenunciadoError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class DenunciadoViewModel : ViewModel() {

    private val _estado = MutableStateFlow(value = Denunciado())
    val estado: StateFlow<Denunciado> = _estado

    // Lista de cargos para el campo "cargo"
    val cargos = listOf(
        "Operario", "Técnico", "Analista", "Ingeniero", "Arquitecto", "Diseñador", "Programador",
        "Desarrollador", "Tester", "Supervisor", "Coordinador", "Gerente de Proyectos",
        "Jefe de Área", "Gerente", "Director", "Secretario", "Contador", "Auditor", "vendedor",
        "minero", "bombero", "carabinero", "Otros"
    )

    // Lista de departamentos, compañías y áreas para el campo "dpto_gcia_area"
    val dptos = listOf(
        "RRHH",
        "Producción",
        "Logística",
        "Marketing",
        "Finanzas",
        "Comercial",
        "Finanzas",
        "Legal",
        "Otros"
    )

    // Actualiza campo nombre
    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    // Actualiza campo apellido paterno
    fun onApellidoPaternoChange(valor: String) {
        _estado.update {
            it.copy(
                apellido_paterno = valor,
                errores = it.errores.copy(apellido_paterno = null)
            )
        }
    }

    // Actualiza campo apellido materno
    fun onApellidoMaternoChange(valor: String) {
        _estado.update {
            it.copy(
                apellido_materno = valor,
                errores = it.errores.copy(apellido_materno = null)
            )
        }
    }

    // Actualiza campo rut
    fun onRutChange(valor: String) {
        _estado.update { it.copy(rut = valor, errores = it.errores.copy(rut = null)) }
    }

    // Actualiza campo cargo
    fun onCargoChange(valor: String) {
        _estado.update { it.copy(cargo = valor, errores = it.errores.copy(cargo = null)) }
    }

    // Actualiza campo dpto_gcia_area
    fun onDptoGciaAreaChange(valor: String) {
        _estado.update {
            it.copy(
                dpto_gcia_area = valor,
                errores = it.errores.copy(dpto_gcia_area = null)
            )
        }
    }

    fun validarFormularioDenunciado(): Boolean {
        val estadoActual = _estado.value
        val errores = DenunciadoError(
            nombre = if (estadoActual.nombre.isBlank()) "El nombre es requerido" else null,
            apellido_paterno = if (estadoActual.apellido_paterno.isBlank()) "El apellido paterno es requerido" else null,
            apellido_materno = if (estadoActual.apellido_materno.isBlank()) "El apellido materno es requerido" else null,
            rut = if (estadoActual.rut.isBlank()) "El rut es requerido" else null,
            cargo = if (estadoActual.cargo.isBlank()) "El cargo es requerido" else null,
            dpto_gcia_area = if (estadoActual.dpto_gcia_area.isBlank()) "El departamento, la compañía o el área es requerido" else null
        )

        val hayError = listOfNotNull(
            errores.nombre,
            errores.apellido_paterno,
            errores.apellido_materno,
            errores.rut,
            errores.cargo,
            errores.dpto_gcia_area
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayError
    }
}