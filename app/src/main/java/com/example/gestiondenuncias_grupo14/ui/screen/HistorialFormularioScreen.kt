package com.example.gestiondenuncias_grupo14.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gestiondenuncias_grupo14.remote.FormularioHistorialDto
import com.example.gestiondenuncias_grupo14.ui.complements.TopBarApp
import com.example.gestiondenuncias_grupo14.viewmodel.FormularioGlobalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialFormulariosScreen(
    navController: NavController,
    globalViewModel: FormularioGlobalViewModel = viewModel()
) {
    val contexto = LocalContext.current
    val historial by globalViewModel.historial.collectAsState()

    // Cargar historial al entrar a la pantalla
    LaunchedEffect(Unit) {
        globalViewModel.cargarHistorial(
            onError = { msg ->
                Toast.makeText(
                    contexto,
                    "Error al cargar historial: $msg",
                    Toast.LENGTH_LONG
                ).show()
            }
        )
    }

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
            if (historial.isEmpty()) {
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
                    itemsIndexed(historial) { index, formulario ->
                        HistorialItem(
                            indice = index + 1,
                            formulario = formulario
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HistorialItem(
    indice: Int,
    formulario: FormularioHistorialDto
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text("Denuncia #$indice", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(4.dp))

            // ── Datos del denunciado ──
            Text("Denunciado:", fontWeight = FontWeight.Bold)
            Text("Nombre: ${formulario.denunciadoNombre} ${formulario.denunciadoApellidoPaterno} ${formulario.denunciadoApellidoMaterno}")
            Text("RUT: ${formulario.denunciadoRut}")
            Text("Cargo: ${formulario.denunciadoCargo}")
            Text("Área: ${formulario.denunciadoArea}")

            Spacer(modifier = Modifier.height(8.dp))

            // ── Datos del representante ──
            Text("Representante:", fontWeight = FontWeight.Bold)
            Text("Nombre: ${formulario.representanteNombre}")
            Text("RUT: ${formulario.representanteRut}")

            Spacer(modifier = Modifier.height(8.dp))

            // ── Tipos de denuncia ──
            Text("Tipos de denuncia:", fontWeight = FontWeight.Bold)
            val tiposLimpios = formulario.tiposSeleccionados
                ?.split(",")
                ?.joinToString(" · ")
                ?: "Sin información"
            Text(tiposLimpios)

            Spacer(modifier = Modifier.height(8.dp))

            // ── Fecha ──
            formulario.fechaCreacion?.let { fecha ->
                val soloFecha = fecha.substringBefore("T")
                Text("Fecha: $soloFecha", style = MaterialTheme.typography.labelSmall)
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
