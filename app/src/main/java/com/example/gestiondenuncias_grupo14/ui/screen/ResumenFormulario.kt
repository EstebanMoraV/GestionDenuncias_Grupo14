package com.example.gestiondenuncias_grupo14.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.gestiondenuncias_grupo14.viewmodel.FormularioPersonaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumenDenunciado(
    navController: NavHostController,
    viewModel: FormularioPersonaViewModel = viewModel()
) {
    val estado by viewModel.estado.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Resumen Formulario") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Resumen de los datos del Denunciado",
                style = MaterialTheme.typography.headlineSmall
            )

            Divider(Modifier.padding(vertical = 4.dp))

            Text("Nombre: ${estado.nombre}")
            Text("Apellido Paterno: ${estado.apellido_paterno}")
            Text("Apellido Materno: ${estado.apellido_materno}")
            Text("RUT: ${estado.rut}")
            Text("Cargo: ${estado.cargo}")
            Text("Departamento / Gerencia / Área: ${estado.dpto_gcia_area}")

            Spacer(modifier = Modifier.height(20.dp))

            // Botón para volver al menú principal
            Button(
                onClick = {
                    navController.navigate("menu") {
                        popUpTo("menu") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al Menú Principal")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ResumenDenunciadoPreview() {
    val mockNavController = androidx.navigation.compose.rememberNavController()
    MaterialTheme {
        ResumenDenunciado(navController = mockNavController)
    }
}
