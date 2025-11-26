package com.example.gestiondenuncias_grupo14.viewmodel

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class EvidenciaViewModelTest {

    private lateinit var viewModel: EvidenciaViewModel

    @Before
    fun setup() {
        viewModel = EvidenciaViewModel()
    }

    @Test
    fun cambiarEvidenciaExistente_actualiza_el_valor_correctamente() {
        // Estado inicial: false
        assertFalse(viewModel.estado.value.evidenciaExistente)

        // Cambiamos a true
        viewModel.cambiarEvidenciaExistente(true)

        // Debe ser true
        assertTrue(viewModel.estado.value.evidenciaExistente)
    }

    @Test
    fun cambiarOtrosAntecedentes_actualiza_correctamente() {
        // Estado inicial: false
        assertFalse(viewModel.estado.value.otrosAntecedentes)

        // Cambiamos a true
        viewModel.cambiarOtrosAntecedentes(true)

        // Debe ser true
        assertTrue(viewModel.estado.value.otrosAntecedentes)
    }
}
