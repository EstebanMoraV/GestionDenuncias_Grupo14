package com.example.gestiondenuncias_grupo14.ui.screen

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.gestiondenuncias_grupo14.viewmodel.FormularioPersonaViewModel

@Composable
fun VictimaScreen(
    navController: NavController,
    viewModel: FormularioPersonaViewModel = viewModel()
) {
    FormularioPersonaScreen(
        titulo = "Formulario Víctima",
        navController = navController,
        viewModel = viewModel,
        siguienteRuta = "tipodenuncia"
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VictimaScreenPreview() {
    val mockNavController = rememberNavController()
    MaterialTheme {
        VictimaScreen(navController = mockNavController)
    }
}

