package com.example.gestiondenuncias_grupo14.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gestiondenuncias_grupo14.ui.complements.TopBarApp
import com.example.gestiondenuncias_grupo14.viewmodel.TipoDenunciaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipoDenunciaScreen(
    navController: NavController,
    viewModel: TipoDenunciaViewModel = viewModel()
) {
    val estado by viewModel.estado.collectAsState()
    val scrollState = rememberScrollState() //estado del scroll

    val opciones = listOf(
        "Acoso laboral",
        "Acoso sexual",
        "Maltrato psicológico",
        "Discriminación"
    )

    Scaffold(
        topBar = { TopBarApp(title = "Tipo de Denuncia", navController = navController) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState) // desplazamiento fluido
                .nestedScroll(TopAppBarDefaults.pinnedScrollBehavior().nestedScrollConnection), // 👈 integración con topbar
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Seleccione el o los tipos de denuncia:",
                style = MaterialTheme.typography.titleMedium
            )

            opciones.forEach { opcion ->
                val seleccionada = opcion in estado.tiposSeleccionados
                val colorFondo by animateColorAsState(
                    if (seleccionada) Color(0xFF4CAF50) else Color(0xFF2196F3),
                    label = "Color de selección"
                )

                Button(
                    onClick = { viewModel.toggleTipo(opcion) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorFondo)
                ) {
                    Text(opcion, color = Color.White)
                }
            }

            Divider(thickness = 1.dp, color = Color.Gray.copy(alpha = 0.4f))

            Text(
                text = "Relación entre víctima y denunciado/a:",
                style = MaterialTheme.typography.titleMedium
            )

            RelacionSwitch(
                texto = "¿Existe una relación asimétrica en que la víctima " +
                        "tiene dependencia directa o indirecta de el/la denunciado/a?",
                valor = estado.relacionAsimetricaVictimaDepende,
                onChange = { viewModel.cambiarRelacionAsimetricaVictima(it) }
            )

            Divider(thickness = 1.dp, color = Color.Gray.copy(alpha = 0.3f))

            RelacionSwitch(
                texto = "¿Existe una relación asimétrica en que el/la denunciado/a " +
                        "tiene dependencia directa o indirecta de la víctima?",
                valor = estado.relacionAsimetricaDenunciadoDepende,
                onChange = { viewModel.cambiarRelacionAsimetricaDenunciado(it) }
            )

            Divider(thickness = 1.dp, color = Color.Gray.copy(alpha = 0.3f))

            RelacionSwitch(
                texto = "¿Existe una relación simétrica en que el/la denunciado/a y la víctima " +
                        "no tienen una dependencia directa ni indirecta, pero se desempeñan en la misma área o equipo?",
                valor = estado.relacionSimetricaMismaArea,
                onChange = { viewModel.cambiarRelacionSimetricaMismaArea(it) }
            )

            Divider(thickness = 1.dp, color = Color.Gray.copy(alpha = 0.3f))

            RelacionSwitch(
                texto = "¿Existe una relación simétrica en que el/la denunciado/a y la víctima no " +
                        "tienen una dependencia directa ni indirecta, pero no se desempeñan en la misma área o equipo?",
                valor = estado.relacionSimetricaDistintaArea,
                onChange = { viewModel.cambiarRelacionSimetricaDistintaArea(it) }
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { navController.navigate("evidencia") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Siguiente")
            }
        }
    }
}

@Composable
fun RelacionSwitch(
    texto: String,
    valor: Boolean,
    onChange: (Boolean) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
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
fun TipoDenunciaScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        TipoDenunciaScreen(navController = navController)
    }
}




