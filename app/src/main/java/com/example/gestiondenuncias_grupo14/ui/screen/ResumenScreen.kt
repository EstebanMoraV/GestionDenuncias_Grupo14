package com.example.gestiondenuncias_grupo14.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import android.widget.Toast
import com.example.gestiondenuncias_grupo14.ui.complements.TopBarApp
import com.example.gestiondenuncias_grupo14.viewmodel.FormularioGlobalViewModel

@Composable
fun ResumenScreen(
    navController: NavController,
    globalViewModel: FormularioGlobalViewModel = viewModel()
) {
    val estado by globalViewModel.estado.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = { TopBarApp(title = "Resumen", navController = navController) }
    ) { padding ->
        ResumenContent(
            padding = padding,
            estadoViewModel = globalViewModel,
            navController = navController,
            context = context
        )
    }
}

@Composable
private fun ResumenContent(
    padding: PaddingValues,
    estadoViewModel: FormularioGlobalViewModel,
    navController: NavController,
    context: android.content.Context
) {
    val estado by estadoViewModel.estado.collectAsState()

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Resumen de la denuncia",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Datos del denunciado
        Text(
            text = "Denunciado:",
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = "${estado.denunciadoNombre} ${estado.denunciadoApellidoPaterno} ${estado.denunciadoApellidoMaterno}"
        )
        Text(text = "RUT: ${estado.denunciadoRut}")
        Text(text = "Cargo: ${estado.denunciadoCargo}")
        Text(text = "Área: ${estado.denunciadoArea}")

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Datos del representante
        Text(
            text = "Representante:",
            style = MaterialTheme.typography.titleSmall
        )
        Text(text = "Nombre: ${estado.representanteNombre}")
        Text(text = "RUT: ${estado.representanteRut}")

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Tipos seleccionados
        Text(
            text = "Tipos de denuncia seleccionados:",
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = estado.tiposSeleccionados
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 🔚 BOTÓN FINAL: ENVÍA AL BACKEND Y VUELVE AL MENÚ
        Button(
            onClick = {
                estadoViewModel.createFormulario(
                    onSuccess = {
                        Toast.makeText(
                            context,
                            "Denuncia enviada correctamente",
                            Toast.LENGTH_LONG
                        ).show()

                        // Limpia el estado global si quieres
                        estadoViewModel.limpiarFormulario()

                        // ⬇️ CAMBIA "menu" POR LA RUTA REAL DE TU MENÚ PRINCIPAL
                        navController.navigate("menu") {
                            popUpTo(0)
                        }
                    },
                    onError = { mensaje ->
                        Toast.makeText(
                            context,
                            "Error al enviar: $mensaje",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
            }
        ) {
            Text("Confirmar y enviar")
        }
    }
}

@Composable
fun ResumenScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        ResumenScreen(navController = navController)
    }
}
