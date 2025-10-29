package com.example.gestiondenuncias_grupo14.ui.screen

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.gestiondenuncias_grupo14.model.Denunciado
import com.example.gestiondenuncias_grupo14.viewmodel.FormularioGlobalViewModel
import com.example.gestiondenuncias_grupo14.viewmodel.FormularioPersonaViewModel

@Composable
fun DenunciadoScreen(
    navController: NavController,
    globalViewModel: FormularioGlobalViewModel = viewModel(),
    viewModel: FormularioPersonaViewModel = viewModel()
) {
    FormularioPersonaScreen(
        titulo = "Formulario Denunciado",
        navController = navController,
        viewModel = viewModel,
        siguienteRuta = "representante",
        onNextClick = {
            // Crear objeto Denunciado con los datos del ViewModel local
            val persona = Denunciado(
                nombre = viewModel.estado.value.nombre,
                apellido_paterno = viewModel.estado.value.apellido_paterno,
                apellido_materno = viewModel.estado.value.apellido_materno,
                rut = viewModel.estado.value.rut,
                cargo = viewModel.estado.value.cargo,
                dpto_gcia_area = viewModel.estado.value.dpto_gcia_area
            )
            // Guardar en el ViewModel global
            globalViewModel.guardarPersona("denunciado", persona)
            // Navegar a la siguiente pantalla
            navController.navigate("representante")
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DenunciadoScreenPreview() {
    val mockNavController = rememberNavController()
    MaterialTheme {
        DenunciadoScreen(navController = mockNavController)
    }
}
