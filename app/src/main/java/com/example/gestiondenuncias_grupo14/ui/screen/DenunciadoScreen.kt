package com.example.gestiondenuncias_grupo14.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import kotlinx.coroutines.launch
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.gestiondenuncias_grupo14.R
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.example.gestiondenuncias_grupo14.viewmodel.DenunciadoViewModel
import androidx.compose.material3.DropdownMenuItem as M3DropdownMenuItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DenunciadoScreen(
    navController: NavController,
    viewModel: DenunciadoViewModel = viewModel()
) {
    val estado by viewModel.estado.collectAsState()

    // Estado y Scope del Drawer (Menú Lateral)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Ítems del menú lateral con su ruta
    val menuItems = listOf(
        Pair("Menú", "menu"),
        Pair("¿Quiénes Somos?", "quienes_somos"),
        Pair("Contáctanos", "contactanos"),
        Pair("Noticias", "noticias")
    )


    // ModalNavigationDrawer: Menú Lateral, envuelve al Scaffold
    ModalNavigationDrawer(
        drawerState = drawerState,
        // Contenido del menú lateral
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(16.dp))
                // Encabezado del menú (Opcional)
                Text(
                    "Menú Principal",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
                Divider()

                // Generar los ítems del menú
                menuItems.forEach { (title, route) ->
                    NavigationDrawerItem(
                        icon = {
                            when (route) {
                                "menu" -> Icon(Icons.Default.Home, contentDescription = title)
                                "quienes_somos" -> Icon(Icons.Default.Info, contentDescription = title)
                                "contactanos" -> Icon(Icons.Default.Email, contentDescription = title)
                                "noticias" -> Icon(Icons.Default.Newspaper, contentDescription = title)
                                else -> Icon(Icons.Default.Home, contentDescription = title)
                            }
                        },
                        label = { Text(title) },
                        selected = false,
                        onClick = {
                            // Cierra el menú y navega
                            scope.launch { drawerState.close() }
                            navController.navigate(route)
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        },
        // Contenido de la pantalla (el Scaffold)
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Acta de Denuncias") },

                        navigationIcon = {
                            // 2. CAMBIO DE ACCIÓN: ABRIR EL MENÚ
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = "Abrir menú")
                            }
                        },
                        actions = {
                            IconButton(onClick = { /*Enviara a otro menu*/ }) {
                                Icon(
                                    painter = painterResource(R.drawable.logo),
                                    contentDescription = "Logo de la aplicación",
                                    tint = colorScheme.onPrimary // Asegura que el logo se vea
                                )
                            }
                        },
                        colors = topAppBarColors(
                            containerColor = colorScheme.primary,
                            titleContentColor = colorScheme.onPrimary
                        )
                    )
                }
            ) { innerPadding ->

                // Aplicamos innerPadding del Scaffold antes del padding interno de 16.dp
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(all = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Subtítulo centrado
                    Text(
                        text = "Ingrese los Datos del Denunciado",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    // Espacio debajo del subtítulo
                    Spacer(modifier = Modifier.height(8.dp))

                    // Campo nombre
                    OutlinedTextField(
                        value = estado.nombre,
                        onValueChange = viewModel::onNombreChange,
                        label = { Text(text = "Nombre") },
                        isError = estado.errores.nombre != null,
                        supportingText = {
                            estado.errores.nombre?.let {
                                Text(text = it, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Campo apellido paterno
                    OutlinedTextField(
                        value = estado.apellido_paterno,
                        onValueChange = viewModel::onApellidoPaternoChange,
                        label = { Text(text = "Apellido Paterno") },
                        isError = estado.errores.apellido_paterno != null,
                        supportingText = {
                            estado.errores.apellido_paterno?.let {
                                Text(text = it, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Campo apellido materno
                    OutlinedTextField(
                        value = estado.apellido_materno,
                        onValueChange = viewModel::onApellidoMaternoChange,
                        label = { Text(text = "Apellido Materno") },
                        isError = estado.errores.apellido_materno != null,
                        supportingText = {
                            estado.errores.apellido_materno?.let {
                                Text(text = it, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Campo rut
                    OutlinedTextField(
                        value = estado.rut,
                        onValueChange = viewModel::onRutChange,
                        label = { Text(text = "Rut") },
                        isError = estado.errores.rut != null,
                        supportingText = {
                            estado.errores.rut?.let {
                                Text(text = it, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // --- Dropdown: Cargo ---
                    var expandedCargo by rememberSaveable { mutableStateOf(false) }
                    val cargos = viewModel.cargos // lista definida en el ViewModel

                    ExposedDropdownMenuBox(
                        expanded = expandedCargo,
                        onExpandedChange = { expandedCargo = it },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = estado.cargo,
                            onValueChange = {}, // lectura solamente: selección por items
                            readOnly = true,
                            label = { Text("Cargo") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCargo) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expandedCargo,
                            onDismissRequest = { expandedCargo = false }
                        ) {
                            cargos.forEach { opcion ->
                                M3DropdownMenuItem(
                                    text = { Text(opcion) },
                                    onClick = {
                                        viewModel.onCargoChange(opcion)
                                        expandedCargo = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // --- Dropdown: Departamento / Compañía / Área ---
                    var expandedDpto by rememberSaveable { mutableStateOf(false) }
                    val dptos = viewModel.dptos // lista definida en el ViewModel

                    ExposedDropdownMenuBox(
                        expanded = expandedDpto,
                        onExpandedChange = { expandedDpto = it },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = estado.dpto_gcia_area,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Departamento / Compañía / Área") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDpto) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expandedDpto,
                            onDismissRequest = { expandedDpto = false }
                        ) {
                            dptos.forEach { opcion ->
                                M3DropdownMenuItem(
                                    text = { Text(opcion) },
                                    onClick = {
                                        viewModel.onDptoGciaAreaChange(opcion)
                                        expandedDpto = false
                                    }
                                )
                            }
                        }

                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    // Botón: Aceptar
                    Button(
                        onClick = {
                            if (viewModel.validarFormularioDenunciado()) {
                                navController.navigate(route = "representante")
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Siguiente")
                    }

                }
            }
        } // Fin del content del ModalNavigationDrawer
    ) // Fin del ModalNavigationDrawer
} // Fin de DenunciadoScreen

@Preview(showBackground = true)
@Composable
fun DenunciadoScreenPreview() {
    val mockNavController = rememberNavController()
    // Necesitas una importación adicional para Divider si lo usas en el preview
    MaterialTheme {
        DenunciadoScreen(
            navController = mockNavController
        )
    }
}

