package com.example.gestiondenuncias_grupo14.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gestiondenuncias_grupo14.viewmodel.FormularioGlobalViewModel
import com.example.gestiondenuncias_grupo14.ui.complements.TopBarApp

@Composable
fun ResumenScreen(
    navController: NavController,
    globalViewModel: FormularioGlobalViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val estado by globalViewModel.estado.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { TopBarApp(title = "Resumen del Formulario", navController = navController) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // denunciado
            SeccionTitulo("Datos del Denunciado")
            PersonaResumen(estado.denunciado)

            Divider()

            // representante
            SeccionTitulo("Datos del Representante")
            PersonaResumen(estado.representante)

            Divider()

            // victima
            SeccionTitulo("Datos de la Víctima")
            PersonaResumen(estado.victima)

            Divider()

            // testigo
            SeccionTitulo("Datos del Testigo (si corresponde)")
            PersonaResumen(estado.testigo)

            Divider()

            // tipo denuncia
            SeccionTitulo("Tipo de Denuncia y Relación")
            if (estado.tipoDenuncia.tiposSeleccionados.isEmpty()) {
                Text("No se seleccionaron tipos de denuncia.")
            } else {
                estado.tipoDenuncia.tiposSeleccionados.forEach {
                    Text("- $it")
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text("Relación asimétrica (víctima depende del denunciado): ${if (estado.tipoDenuncia.relacionAsimetricaVictimaDepende) "Sí" else "No"}")
            Text("Relación asimétrica (denunciado depende de la víctima): ${if (estado.tipoDenuncia.relacionAsimetricaDenunciadoDepende) "Sí" else "No"}")
            Text("Relación simétrica (misma área): ${if (estado.tipoDenuncia.relacionSimetricaMismaArea) "Sí" else "No"}")
            Text("Relación simétrica (distinta área): ${if (estado.tipoDenuncia.relacionSimetricaDistintaArea) "Sí" else "No"}")

            Divider()

            // evidencia
            SeccionTitulo("Evidencia y Antecedentes")
            Text("¿Existe evidencia?: ${if (estado.evidencia.evidenciaExistente) "Sí" else "No"}")
            Text("¿Otros antecedentes similares?: ${if (estado.evidencia.otrosAntecedentes) "Sí" else "No"}")
            Text("¿Fue informada previamente?: ${if (estado.evidencia.informadaPreviamente) "Sí" else "No"}")
            Text("¿Cuenta con testigos?: ${if (estado.evidencia.cuentaConTestigos) "Sí" else "No"}")

            Divider()

            // relato
            SeccionTitulo("Relato de la situación")
            if (estado.relato.texto.isNotBlank()) {
                Text(estado.relato.texto)
            } else {
                Text("No se ingresó relato escrito.")
            }

            Spacer(modifier = Modifier.height(8.dp))
            if (estado.relato.audioPath != null) {
                Text(" Archivo de audio grabado: ${estado.relato.audioPath}")
            } else {
                Text("No se grabó audio.")
            }

            Divider()

            // boton final
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { globalViewModel.limpiarFormulario() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Limpiar formulario")
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { navController.navigate("menu") },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Volver al menú principal")
            }
        }
    }
}

@Composable
fun SeccionTitulo(texto: String) {
    Text(
        text = texto,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

@Composable
fun PersonaResumen(persona: com.example.gestiondenuncias_grupo14.model.Denunciado) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text("Nombre: ${persona.nombre}")
        Text("Apellido Paterno: ${persona.apellido_paterno}")
        Text("Apellido Materno: ${persona.apellido_materno}")
        Text("RUT: ${persona.rut}")
        Text("Cargo: ${persona.cargo}")
        Text("Departamento / Área: ${persona.dpto_gcia_area}")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ResumenScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        ResumenScreen(navController = navController)
    }
}


