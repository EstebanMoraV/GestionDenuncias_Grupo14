package com.example.gestiondenuncias_grupo14.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gestiondenuncias_grupo14.viewmodel.FormularioDBViewModel
import com.example.gestiondenuncias_grupo14.data.local.entity.FormularioEntity
import com.example.gestiondenuncias_grupo14.ui.complements.TopBarApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialFormulariosScreen(
    navController: NavController,
    dbViewModel: FormularioDBViewModel = viewModel()
) {

    val formularios by dbViewModel.formularios.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopBarApp(title = "Historial de Formularios", navController = navController)
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (formularios.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay formularios guardados",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(formularios) { formulario ->
                        FormularioItem(
                            formulario = formulario,
                            onDelete = { dbViewModel.eliminar(formulario) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FormularioItem(
    formulario: FormularioEntity,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            //  Datos del denunciado
            Text("Denunciado:", fontWeight = FontWeight.Bold)
            Text("Nombre: ${formulario.denunciadoNombre}")
            Text("Apellido Paterno: ${formulario.denunciadoApellidoPaterno}")
            Text("Apellido Materno: ${formulario.denunciadoApellidoMaterno}")
            Text("RUT: ${formulario.denunciadoRut}")
            Text("Cargo: ${formulario.denunciadoCargo}")
            Text("Área: ${formulario.denunciadoArea}")

            Spacer(modifier = Modifier.height(8.dp))

            //  Datos del representante
            Text("Representante:", fontWeight = FontWeight.Bold)
            Text("Nombre: ${formulario.representanteNombre}")
            Text("RUT: ${formulario.representanteRut}")

            Spacer(modifier = Modifier.height(8.dp))

            //  Tipo de denuncia resumido
            Text("Tipo de denuncia:", fontWeight = FontWeight.Bold)
            Text(formulario.tiposSeleccionados.ifEmpty { "Sin información" })

            Spacer(modifier = Modifier.height(8.dp))


            //  Botón de eliminar
            OutlinedButton(
                onClick = onDelete,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                Spacer(modifier = Modifier.width(4.dp))
                Text("Eliminar")
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HistorialFormulariosPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        HistorialFormulariosScreen(navController = navController)
    }
}

