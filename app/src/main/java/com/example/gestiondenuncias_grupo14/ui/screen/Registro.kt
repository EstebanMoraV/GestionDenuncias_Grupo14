package com.example.gestiondenuncias_grupo14.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gestiondenuncias_grupo14.ui.complements.TopBarApp
import com.example.gestiondenuncias_grupo14.ui.complements.LoadingIndicator
import com.example.gestiondenuncias_grupo14.ui.complements.CustomDialog
import com.example.gestiondenuncias_grupo14.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.livedata.observeAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registro(navController: NavController? = null, viewModel: UsuarioViewModel = viewModel()) {
    // Variables de entrada del formulario
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

    // Listas de opciones
    val empresas = listOf("Productos Cave", "Diprovet", "Lubricantes Internacionales")
    val cargos = viewModel.cargos
    val dptos = viewModel.dptos

    // Scroll y snackbar
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Estado de carga
    var isLoading by remember { mutableStateOf(false) }

    // Diálogo de confirmación (Cancelar)
    var showCancelDialog by remember { mutableStateOf(false) }

    // Observamos el resultado remoto
    val resultado by viewModel.resultado.observeAsState()

    Scaffold(
        topBar = {
            if (navController != null) {
                TopBarApp(title = "Formulario de Registro", navController = navController)
            } else {
                TopAppBar(
                    title = { Text("Formulario de Registro") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorScheme.primary,
                        titleContentColor = colorScheme.onPrimary
                    )
                )
            }
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

                RegistroTextField("Rut", "12345678-9", rut) { rut = it }
                RegistroTextField("Nombre", "Ingrese su nombre", nombre) { nombre = it }
                RegistroTextField("Apellido", "Ingrese su apellido", apellido) { apellido = it }
                RegistroTextField("Correo", "pepito123@gmail.com", correo) { correo = it }
                RegistroTextField("Contraseña", "Ingrese su contraseña", contrasena) { contrasena = it }
                RegistroTextField("Confirmar", "Repita su contraseña", confirmarContrasena) { confirmarContrasena = it }

                ComboBoxField(
                    "Empresa",
                    empresa,
                    expandedEmpresa,
                    empresas,
                    onClick = { empresa = it; expandedEmpresa = false },
                    onExpandedChange = { expandedEmpresa = !expandedEmpresa }
                )
                ComboBoxField(
                    "Cargo",
                    cargo,
                    expandedCargo,
                    cargos,
                    onClick = { cargo = it; expandedCargo = false },
                    onExpandedChange = { expandedCargo = !expandedCargo }
                )
                ComboBoxField(
                    "Depto - Área",
                    dep_area,
                    expandedDepto,
                    dptos,
                    onClick = { dep_area = it; expandedDepto = false },
                    onExpandedChange = { expandedDepto = !expandedDepto }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
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

                            isLoading = true

                            viewModel.registrarUsuarioRemoto(
                                rut, nombre, apellido, correo, contrasena, empresa, cargo, dep_area
                            )
                        },
                        modifier = Modifier.weight(1f),
                        enabled = !isLoading
                    ) {
                        Text("Registrar")
                    }


                    OutlinedButton(
                        onClick = { showCancelDialog = true },
                        modifier = Modifier.weight(1f),
                        enabled = !isLoading
                    ) {
                        Text("Cancelar")
                    }
                }
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingIndicator()
                }
            }
        }
    }

    // Manejo del resultado remoto del ViewModel
    LaunchedEffect(resultado) {
        resultado?.let {
            isLoading = false
            scope.launch {
                snackbarHostState.showSnackbar(it)
                if (it.contains("exitosamente")) {
                    kotlinx.coroutines.delay(2000)
                    navController?.navigate("login")
                }
            }
            viewModel.resetResultado() // Limpia el valor para que no se repita
        }
    }



    CustomDialog(
        showDialog = showCancelDialog,
        title = "Cancelar registro",
        message = "¿Deseas cancelar y volver al login?",
        confirmText = "Sí, cancelar",
        dismissText = "No, continuar",
        onConfirm = {
            showCancelDialog = false
            navController?.navigate("login")
        },
        onDismiss = { showCancelDialog = false }
    )
}

// -----------------------------------------------------------------------------
// Componentes reutilizables
// -----------------------------------------------------------------------------

@Composable
fun RegistroTextField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit
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
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(placeholder) },
            modifier = Modifier
                .weight(1f)
                .alignByBaseline()
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
                modifier = Modifier
                    .menuAnchor()
                    .weight(1f)
                    .alignByBaseline()
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
