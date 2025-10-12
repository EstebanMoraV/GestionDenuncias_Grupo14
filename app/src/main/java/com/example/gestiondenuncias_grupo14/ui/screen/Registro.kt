package com.example.gestiondenuncias_grupo14.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registro(navController: NavController? = null){
    // Variables
    var rut by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var contrasena2 by remember { mutableStateOf("") }
    var empresa by remember { mutableStateOf("") }
    var cargo by remember { mutableStateOf("") }
    var dep_area by remember { mutableStateOf("") }

    // Estado para el ComboBox
    var expanded by remember { mutableStateOf(false) }

    // Lista de empresas
    val empresas = listOf("Productos Cave", "Diprovet", "Lubricantes Internacionales")

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Formulario de Registro") },
                colors = topAppBarColors(
                    containerColor = colorScheme.primary,
                    titleContentColor = colorScheme.onPrimary
                )
            )
        }
    ) {
        innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)              // Respeta el padding interno del Scaffold
                .fillMaxSize()                      // Ocupa todo el espacio disponible
                .padding(24.dp),                    // Padding uniforme alrededor
            verticalArrangement = Arrangement.spacedBy(24.dp), // Espaciado uniforme entre elementos
            horizontalAlignment = Alignment.CenterHorizontally // Centra los elementos horizontalmente
        ){
            Text(
                text = "Registro de Usuario",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            // Acá hice una fila dentro de la columna
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Text(
                    text = "Rut",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.alignByBaseline()
                        .width(60.dp)
                )

                OutlinedTextField(
                    value = rut,
                    onValueChange = {rut = it},
                    label = { Text("12345678-9") },
                    modifier = Modifier
                        .alignByBaseline() // Ocupa el espacio restante
                        .weight(1f)
                )
            }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Text(
                    text = "Nombre",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.alignByBaseline()
                        .width(60.dp)
                )

                OutlinedTextField(
                    value = nombre,
                    onValueChange = {nombre = it},
                    label = { Text("Ingrese aquí su nombre") },
                    modifier = Modifier
                        .weight(1f) // Ocupa el espacio restante
                        .alignByBaseline()
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Text(
                    text = "Apellido",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.alignByBaseline()
                        .width(60.dp)
                )

                OutlinedTextField(
                    value = apellido,
                    onValueChange = {apellido = it},
                    label = { Text("Ingrese aquí su apellido") },
                    modifier = Modifier
                        .weight(1f) // Ocupa el espacio restante
                        .alignByBaseline()
                )
            }

            // ComboBox Empresa
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Text(
                    text = "Empresa",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.alignByBaseline()
                        .width(60.dp)
                )

                // Aquí viene la creación del combobox
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = empresa,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Seleccione una empresa") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .weight(1f)
                            .alignByBaseline()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        empresas.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    empresa = opcion
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

// ------------------------------------------------------------------------------

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Text(
                    text = "Cargo",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.alignByBaseline()
                        .width(60.dp)
                )

                OutlinedTextField(
                    value = cargo,
                    onValueChange = {cargo = it},
                    label = { Text("Ingrese aquí su cargo") },
                    modifier = Modifier
                        .weight(1f) // Ocupa el espacio restante
                        .alignByBaseline()
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Text(
                    text = "Depto - Area",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.alignByBaseline()
                        .width(60.dp)
                )

                OutlinedTextField(
                    value = dep_area,
                    onValueChange = {dep_area = it},
                    label = { Text("Ingrese aquí su area o departamento") },
                    modifier = Modifier
                        .weight(1f) // Ocupa el espacio restante
                        .alignByBaseline()
                )
            }

            Button(
                onClick = { /* Acción futura*/ },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Registrar") }

        }
    }
}

// Previsualización
@Preview(showBackground = true)
@Composable
fun RegistroPreview(){
    MaterialTheme{
        Registro()
    }
}