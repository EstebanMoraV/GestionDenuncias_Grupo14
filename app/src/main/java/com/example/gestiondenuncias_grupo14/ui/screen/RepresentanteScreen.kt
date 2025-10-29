package com.example.gestiondenuncias_grupo14.ui.screen

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.gestiondenuncias_grupo14.model.Denunciado
import com.example.gestiondenuncias_grupo14.viewmodel.FormularioPersonaViewModel
import com.example.gestiondenuncias_grupo14.viewmodel.FormularioGlobalViewModel
import com.example.gestiondenuncias_grupo14.ui.complements.CustomDialog

@Composable
fun RepresentanteScreen(
    navController: NavController,
    globalViewModel: FormularioGlobalViewModel = viewModel(),
    viewModel: FormularioPersonaViewModel = viewModel()
) {
    var mostrarDialogo by remember { mutableStateOf(false) }

    // Diálogo reutilizable: ¿Usted es la víctima?
    CustomDialog(
        showDialog = mostrarDialogo,
        title = "Confirmar identidad",
        message = "¿Usted es la víctima de lo denunciado?",
        confirmText = "Sí",
        dismissText = "No",
        onConfirm = {
            mostrarDialogo = false
            // Si es la víctima, abre el siguiente formulario
            navController.navigate("tipodenuncia")
        },
        onDismiss = {
            mostrarDialogo = false
            // Si no es la víctima, abre el formulario de víctima
            navController.navigate("victima")
        }
    )

    // Reutiliza el formulario base
    FormularioPersonaScreen(
        titulo = "Formulario Representante",
        navController = navController,
        viewModel = viewModel,
        // ruta por defecto
        siguienteRuta = "victima",
        onNextClick = {
            if (viewModel.validarFormulario()) {
                // Construye el objeto con tu modelo real
                val persona = Denunciado(
                    nombre = viewModel.estado.value.nombre,
                    apellido_paterno = viewModel.estado.value.apellido_paterno,
                    apellido_materno = viewModel.estado.value.apellido_materno,
                    rut = viewModel.estado.value.rut,
                    cargo = viewModel.estado.value.cargo,
                    dpto_gcia_area = viewModel.estado.value.dpto_gcia_area
                )
                // Guarda en el estado global como "representante"
                globalViewModel.guardarPersona("representante", persona)
                // Abre el diálogo de confirmación
                mostrarDialogo = true
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RepresentanteScreenPreview() {
    val mockNavController = rememberNavController()
    MaterialTheme {
        RepresentanteScreen(navController = mockNavController)
    }
}
