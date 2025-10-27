package com.example.gestiondenuncias_grupo14.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gestiondenuncias_grupo14.viewmodel.UsuarioViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registro(navController: NavController? = null, viewModel: UsuarioViewModel = viewModel()) {
    // Variables de entrada
    var rut by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }
    var empresa by remember { mutableStateOf("") }
    var cargo by remember { mutableStateOf("") }
    var dep_area by remember { mutableStateOf("") }

    // Estado para los ComboBox
    var expandedEmpresa by remember { mutableStateOf(false) }
    var expandedCargo by remember { mutableStateOf(false) }
    var expandedDepto by remember { mutableStateOf(false) }

    // Listas
    val empresas = listOf("Productos Cave", "Diprovet", "Lubricantes Internacionales")
    val cargos = viewModel.cargos
    val dptos = viewModel.dptos

    // Scroll y snackbar
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Estado de carga
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Formulario de Registro") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.primary,
                    titleContentColor = colorScheme.onPrimary
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Registro de Usuario",
                    style = MaterialTheme.typography.titleLarge,
                    color = colorScheme.primary
                )

                // Campos de texto normales
                RegistroTextField("Rut", "12345678-9", rut) { rut = it }
                RegistroTextField("Nombre", "Ingrese su nombre", nombre) { nombre = it }
                RegistroTextField("Apellido", "Ingrese su apellido", apellido) { apellido = it }
                RegistroTextField("Correo", "pepito123@gmail.com", correo) { correo = it }
                RegistroTextField("Contraseña", "Ingrese su contraseña", contrasena) { contrasena = it }
                RegistroTextField("Confirmar", "Repita su contraseña", confirmarContrasena) { confirmarContrasena = it }

                // Combo Empresa
                ComboBoxField("Empresa", empresa, expandedEmpresa, empresas, onClick = {
                    empresa = it; expandedEmpresa = false
                }) { expandedEmpresa = !expandedEmpresa }

                // Combo Cargo
                ComboBoxField("Cargo", cargo, expandedCargo, cargos, onClick = {
                    cargo = it; expandedCargo = false
                }) { expandedCargo = !expandedCargo }

                // Combo Departamento
                ComboBoxField("Depto - Área", dep_area, expandedDepto, dptos, onClick = {
                    dep_area = it; expandedDepto = false
                }) { expandedDepto = !expandedDepto }

                Button(
                    onClick = {
                        val error = viewModel.validarCampos(
                            rut, nombre, apellido, correo, contrasena, empresa, cargo, dep_area
                        )
                        if (error != null) {
                            scope.launch { snackbarHostState.showSnackbar(error) }
                            return@Button
                        }

                        if (contrasena != confirmarContrasena) {
                            scope.launch { snackbarHostState.showSnackbar("Las contraseñas no coinciden") }
                            return@Button
                        }

                        // Simular carga
                        scope.launch {
                            isLoading = true
                            delay(1500) // simula espera (ej. validación o backend)
                            val exito = viewModel.registrarUsuario(
                                rut, nombre, apellido, correo, contrasena, empresa, cargo, dep_area
                            )
                            isLoading = false

                            if (exito) {
                                snackbarHostState.showSnackbar("Usuario registrado con éxito")
                                navController?.navigate("login")
                            } else {
                                snackbarHostState.showSnackbar("El usuario ya está registrado")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrar")
                }
            }

            // 🔄 Indicador de carga animado
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                        Spacer(Modifier.height(12.dp))
                        Text("Cargando...", color = colorScheme.primary)
                    }
                }
            }
        }
    }
}

@Composable
fun RegistroTextField(label: String, placeholder: String, value: String, onValueChange: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = colorScheme.primary,
            modifier = Modifier.alignByBaseline().width(90.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(placeholder) },
            modifier = Modifier.weight(1f).alignByBaseline()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComboBoxField(
    label: String,
    value: String,
    expanded: Boolean,
    options: List<String>,
    onClick: (String) -> Unit,
    onExpandedChange: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = colorScheme.primary,
            modifier = Modifier.alignByBaseline().width(90.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { onExpandedChange() }
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = {},
                readOnly = true,
                label = { Text("Seleccione") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().weight(1f).alignByBaseline()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange() }
            ) {
                options.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = { onClick(opcion) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroPreview() {
    MaterialTheme {
        Registro()
    }
}
