package com.example.gestiondenuncias_grupo14.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gestiondenuncias_grupo14.ui.complements.TopBarApp
import com.example.gestiondenuncias_grupo14.viewmodel.FormularioPersonaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioPersonaScreen(
    titulo: String,
    navController: NavController,
    viewModel: FormularioPersonaViewModel,
    siguienteRuta: String,
    onNextClick: (() -> Unit)? = null // nuevo parámetro opcional
) {
    val estado by viewModel.estado.collectAsState()

    var expandedCargo by remember { mutableStateOf(false) }
    var expandedDpto by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopBarApp(title = titulo, navController = navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = estado.nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = { Text("Nombre") },
                isError = estado.errores.nombre != null,
                modifier = Modifier.fillMaxWidth()
            )
            estado.errores.nombre?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            OutlinedTextField(
                value = estado.apellido_paterno,
                onValueChange = { viewModel.onApellidoPaternoChange(it) },
                label = { Text("Apellido Paterno") },
                isError = estado.errores.apellido_paterno != null,
                modifier = Modifier.fillMaxWidth()
            )
            estado.errores.apellido_paterno?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            OutlinedTextField(
                value = estado.apellido_materno,
                onValueChange = { viewModel.onApellidoMaternoChange(it) },
                label = { Text("Apellido Materno") },
                isError = estado.errores.apellido_materno != null,
                modifier = Modifier.fillMaxWidth()
            )
            estado.errores.apellido_materno?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            OutlinedTextField(
                value = estado.rut,
                onValueChange = { viewModel.onRutChange(it) },
                label = { Text("RUT") },
                isError = estado.errores.rut != null,
                modifier = Modifier.fillMaxWidth()
            )
            estado.errores.rut?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            // Cargo
            ExposedDropdownMenuBox(
                expanded = expandedCargo,
                onExpandedChange = { expandedCargo = !expandedCargo }
            ) {
                OutlinedTextField(
                    value = estado.cargo,
                    onValueChange = {},
                    label = { Text("Cargo") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCargo) },
                    readOnly = true,
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    isError = estado.errores.cargo != null
                )
                ExposedDropdownMenu(
                    expanded = expandedCargo,
                    onDismissRequest = { expandedCargo = false }
                ) {
                    viewModel.cargos.forEach { cargo ->
                        DropdownMenuItem(
                            text = { Text(cargo) },
                            onClick = {
                                viewModel.onCargoChange(cargo)
                                expandedCargo = false
                            }
                        )
                    }
                }
            }

            estado.errores.cargo?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            // Departamento
            ExposedDropdownMenuBox(
                expanded = expandedDpto,
                onExpandedChange = { expandedDpto = !expandedDpto }
            ) {
                OutlinedTextField(
                    value = estado.dpto_gcia_area,
                    onValueChange = {},
                    label = { Text("Departamento / Gerencia / Área") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDpto) },
                    readOnly = true,
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    isError = estado.errores.dpto_gcia_area != null
                )
                ExposedDropdownMenu(
                    expanded = expandedDpto,
                    onDismissRequest = { expandedDpto = false }
                ) {
                    viewModel.dptos.forEach { dpto ->
                        DropdownMenuItem(
                            text = { Text(dpto) },
                            onClick = {
                                viewModel.onDptoGciaAreaChange(dpto)
                                expandedDpto = false
                            }
                        )
                    }
                }
            }

            estado.errores.dpto_gcia_area?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón siguiente
            Button(
                onClick = {
                    val valido = viewModel.validarFormulario()
                    if (valido) {
                        if (onNextClick != null) {
                            onNextClick()
                        } else {
                            navController.navigate(siguienteRuta)
                        }
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar("Completa todos los campos correctamente")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Siguiente")
            }


            // Botón volver
            Button(
                onClick = {
                    navController.navigate("menu") {
                        popUpTo("menu") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Volver al Menú")
            }
        }
    }
}


