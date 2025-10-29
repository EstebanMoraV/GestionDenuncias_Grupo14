package com.example.gestiondenuncias_grupo14.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gestiondenuncias_grupo14.ui.complements.TopBarApp
import com.example.gestiondenuncias_grupo14.viewmodel.EvidenciaData
import com.example.gestiondenuncias_grupo14.viewmodel.FormularioGlobalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EvidenciaScreen(
    navController: NavController,
    globalViewModel: FormularioGlobalViewModel = viewModel()
) {
    var evidenciaExistente by remember { mutableStateOf(false) }
    var otrosAntecedentes by remember { mutableStateOf(false) }
    var informadaPreviamente by remember { mutableStateOf(false) }
    var cuentaConTestigos by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { TopBarApp(title = "Evidencia", navController = navController) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "SOBRE LAS PRESUNTAS SITUACIONES DENUNCIADAS",
                style = MaterialTheme.typography.titleMedium
            )

            Divider(thickness = 1.dp, color = Color.Gray.copy(alpha = 0.4f))

            EvidenciaSwitch(
                texto = "¿Existe evidencia de lo denunciado (email, fotos, etc.)?",
                valor = evidenciaExistente,
                onChange = { evidenciaExistente = it }
            )

            Divider(thickness = 1.dp, color = Color.Gray.copy(alpha = 0.4f))

            EvidenciaSwitch(
                texto = "¿Existe conocimiento de otros antecedentes de índole similar?",
                valor = otrosAntecedentes,
                onChange = { otrosAntecedentes = it }
            )

            Divider(thickness = 1.dp, color = Color.Gray.copy(alpha = 0.4f))

            EvidenciaSwitch(
                texto = "¿La situación denunciada fue informada previamente en otra instancia similar (Jefatura, supervisor, mediación laboral, etc.)?",
                valor = informadaPreviamente,
                onChange = { informadaPreviamente = it }
            )

            Divider(thickness = 1.dp, color = Color.Gray.copy(alpha = 0.4f))

            EvidenciaSwitch(
                texto = "¿Cuenta con testigos de los sucesos?",
                valor = cuentaConTestigos,
                onChange = { cuentaConTestigos = it }
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    val datos = EvidenciaData(
                        evidenciaExistente = evidenciaExistente,
                        otrosAntecedentes = otrosAntecedentes,
                        informadaPreviamente = informadaPreviamente,
                        cuentaConTestigos = cuentaConTestigos
                    )
                    globalViewModel.guardarEvidencia(datos)

                    if (cuentaConTestigos)
                        navController.navigate("testigo")
                    else
                        navController.navigate("relato")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Siguiente")
            }
        }
    }
}

@Composable
fun EvidenciaSwitch(
    texto: String,
    valor: Boolean,
    onChange: (Boolean) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(text = texto, style = MaterialTheme.typography.bodyMedium)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (valor) "Sí" else "No")
            Switch(
                checked = valor,
                onCheckedChange = onChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF4CAF50),
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color(0xFFBDBDBD)
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EvidenciaScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        EvidenciaScreen(navController = navController)
    }
}

