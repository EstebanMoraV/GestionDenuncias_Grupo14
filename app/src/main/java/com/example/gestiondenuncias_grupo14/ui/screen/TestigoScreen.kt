package com.example.gestiondenuncias_grupo14.ui.screen

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.gestiondenuncias_grupo14.model.Denunciado
import com.example.gestiondenuncias_grupo14.viewmodel.FormularioPersonaViewModel
import com.example.gestiondenuncias_grupo14.viewmodel.FormularioGlobalViewModel

@Composable
fun TestigoScreen(
    navController: NavController,
    globalViewModel: FormularioGlobalViewModel = viewModel(),
    viewModel: FormularioPersonaViewModel = viewModel()
) {
    FormularioPersonaScreen(
        titulo = "Formulario Testigo",
        navController = navController,
        viewModel = viewModel,
        siguienteRuta = "relato",
        onNextClick = {
            if (viewModel.validarFormulario()) {
                val persona = Denunciado(
                    nombre = viewModel.estado.value.nombre,
                    apellido_paterno = viewModel.estado.value.apellido_paterno,
                    apellido_materno = viewModel.estado.value.apellido_materno,
                    rut = viewModel.estado.value.rut,
                    cargo = viewModel.estado.value.cargo,
                    dpto_gcia_area = viewModel.estado.value.dpto_gcia_area
                )
                globalViewModel.guardarPersona("testigo", persona)
                navController.navigate("relato")
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TestigoScreenPreview() {
    val mockNavController = rememberNavController()
    MaterialTheme {
        TestigoScreen(navController = mockNavController)
    }
}

