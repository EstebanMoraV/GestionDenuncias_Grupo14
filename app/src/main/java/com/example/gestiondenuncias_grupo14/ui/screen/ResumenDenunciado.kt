package com.example.gestiondenuncias_grupo14.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gestiondenuncias_grupo14.viewmodel.DenunciadoViewModel

@Composable
fun ResumenDenunciado(viewModel: DenunciadoViewModel, navController: NavHostController) {
    val estado by viewModel.estado.collectAsState()

    Column (Modifier.padding(all = 16.dp))  {
        Text(text = "Resumen de los datos del Denunciado", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Nombre: ${estado.nombre}")
        Text(text = "Apellido Paterno: ${estado.apellido_paterno}")
        Text(text = "Apellido Materno: ${estado.apellido_materno}")
        Text(text = "Rut: ${estado.rut}")
        Text(text = "Cargo: ${estado.cargo}")
        Text(text = "Departamento / Compañía / Área: ${estado.dpto_gcia_area}")

    }

}