package com.example.gestiondenuncias_grupo14.ui.screen

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.tooling.preview.Preview
import com.example.gestiondenuncias_grupo14.viewmodel.FormularioPersonaViewModel
import com.example.gestiondenuncias_grupo14.ui.complements.CustomDialog

@Composable
fun RepresentanteScreen(
    navController: NavController,
    viewModel: FormularioPersonaViewModel = viewModel()
) {
    var mostrarDialogo by remember { mutableStateOf(false) }

    // Diálogo reutilizable desde complements
    CustomDialog(
        showDialog = mostrarDialogo,
        title = "Confirmar identidad",
        message = "¿Usted es la víctima de lo denunciado?",
        confirmText = "Sí",
        dismissText = "No",
        onConfirm = {
            mostrarDialogo = false
            navController.navigate("victimaDirecta") // Nueva ruta
        },
        onDismiss = {
            mostrarDialogo = false
            navController.navigate("victima")
        }
    )

    // Reutilizamos el formulario con onNextClick
    FormularioPersonaScreen(
        titulo = "Formulario Representante",
        navController = navController,
        viewModel = viewModel,
        siguienteRuta = "victima", // ruta por defecto
        onNextClick = { mostrarDialogo = true } // intercepta el click
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RepresentanteScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        RepresentanteScreen(navController = navController)
    }
}
