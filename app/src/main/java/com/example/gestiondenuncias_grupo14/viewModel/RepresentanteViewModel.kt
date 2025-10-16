package com.example.gestiondenuncias_grupo14.viewModel

import androidx.lifecycle.ViewModel
import com.example.gestiondenuncias_grupo14.model.Representante
import com.example.gestiondenuncias_grupo14.model.RepresentanteError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RepresentanteViewModel : ViewModel() {

    private val _estado = MutableStateFlow(value = Representante())

    val estado: StateFlow<Representante> = _estado

    // Lista de cargos
    val cargos = listOf(
        "Operario", "Técnico", "Analista", "Ingeniero", "Arquitecto", "Diseñador", "Programador",
        "Desarrollador", "Tester", "Supervisor", "Coordinador", "Gerente de Proyectos",
        "Jefe de Área", "Gerente", "Director", "Secretario", "Contador", "Auditor", "vendedor",
        "minero", "bombero", "carabinero", "Otros"
    )

    // Lista de Departamentos
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

    fun cambiarNombre(nuevoNombre: String) {
        _estado.update { it.copy(nombreRep = nuevoNombre, errores = it.errores.copy(nombre = null)) }
    }

    fun cambiarApellidoPaterno(nuevoApellido : String) {
        _estado.update { it.copy(
            apellido_paterno = nuevoApellido,
            errores = it.errores.copy(apellido_paterno = null)
            ) }
    }

    fun cambiarApellidoMaterno(nuevoApellido: String) {
        _estado.update {
            it.copy(
                apellido_materno = nuevoApellido,
                errores = it.errores.copy(apellido_materno = null)
            )
        }
    }

    fun cambiarRut(nuevoRut : String) {
        _estado.update {
            it.copy(
                rut = nuevoRut,
                errores = it.errores.copy(rut = null)
            )
        }
    }

    fun cambiarCargo(nuevoCargo: String) {
        _estado.update {
            it.copy(
                cargo = nuevoCargo,
                errores = it.errores.copy(cargo = null)
            )
        }
    }

    fun cambiarDpto(nuevoDpto: String) {
        _estado.update {
            it.copy(
                dpto_gcia_area = nuevoDpto,
                errores = it.errores.copy(dpto_gcia_area = null)
            )
        }
    }


    // Validación de formulario
    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        val errores = RepresentanteError(
            nombre = if (estadoActual.nombreRep.isBlank()) "El nombre es requerido" else null,
            apellido_paterno = if (estadoActual.apellido_paterno.isBlank()) "El apellido paterno es requerido" else null,
            apellido_materno = if (estadoActual.apellido_materno.isBlank()) "El apellido materno es requerido" else null,
            rut = if (estadoActual.rut.isBlank()) "El Rut es requerido" else null,
            cargo = if (estadoActual.cargo.isBlank()) "Favor, elija una opción" else null,
            dpto_gcia_area = if (estadoActual.dpto_gcia_area.isBlank()) "favor elija una opción" else null
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