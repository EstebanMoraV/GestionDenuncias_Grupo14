package com.example.gestiondenuncias_grupo14.ui.complements

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarApp(
    title: String,
    navController: NavController
) {
    var expanded by remember { mutableStateOf(false) }
    var showExitDialog by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            Box {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menú"
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Menú Principal") },
                        leadingIcon = { Icon(Icons.Filled.Home, contentDescription = null) },
                        onClick = {
                            expanded = false
                            navController.navigate("menu")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Historial") },
                        leadingIcon = { Icon(Icons.Filled.History, contentDescription = null) },
                        onClick = {
                            expanded = false
                            navController.navigate("historial")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("¿Quiénes Somos?") },
                        leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                        onClick = {
                            expanded = false
                            navController.navigate("quiensomos")
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Cerrar Sesión") },
                        leadingIcon = { Icon(Icons.Filled.Settings, contentDescription = null) },
                        onClick = {
                            expanded = false
                            showExitDialog = true // Mostrar diálogo
                        }
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF14284B),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White

        )
    )

    // ✅ Diálogo de confirmación (usando tu CustomDialog)
    CustomDialog(
        showDialog = showExitDialog,
        title = "Confirmar salida",
        message = "¿Desea cerrar la sesión?",
        confirmText = "Sí, salir",
        dismissText = "Cancelar",
        onConfirm = {
            showExitDialog = false
            navController.navigate("login") {
                popUpTo("menu") { inclusive = true } // Limpia el back stack
            }
        },
        onDismiss = { showExitDialog = false }
    )
}


@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(8.dp))
        Text("Cargando...", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun CustomDialog(
    showDialog: Boolean,
    title: String = "Confirmar",
    message: String,
    confirmText: String = "Aceptar",
    dismissText: String = "Cancelar",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(title, style = MaterialTheme.typography.titleMedium) },
            text = { Text(message, style = MaterialTheme.typography.bodyMedium) },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text(confirmText, color = MaterialTheme.colorScheme.primary)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(dismissText, color = MaterialTheme.colorScheme.error)
                }
            }
        )
    }
}
