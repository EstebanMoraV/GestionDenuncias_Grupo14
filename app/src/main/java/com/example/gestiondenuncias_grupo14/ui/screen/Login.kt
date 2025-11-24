package com.example.gestiondenuncias_grupo14.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gestiondenuncias_grupo14.R
import com.example.gestiondenuncias_grupo14.viewmodel.UsuarioViewModel
import com.example.gestiondenuncias_grupo14.ui.complements.TopBarApp
import com.example.gestiondenuncias_grupo14.ui.complements.LoadingIndicator
import com.example.gestiondenuncias_grupo14.ui.complements.CustomDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController? = null, viewModel: UsuarioViewModel = viewModel()) {

    // Estados UI
    var username by remember { mutableStateOf("es@gmail.com") }
    var password by remember { mutableStateOf("123456") }
    var cargando by remember { mutableStateOf(false) }
    var showExitDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    // Registro automático del usuario por defecto (solo si no existe sesión)
    LaunchedEffect(Unit) {
        if (viewModel.usuarioActual == null) {
            viewModel.registrarUsuario(
                rut = "19829659-7",
                nombre = "Esteban",
                apellido = "Mora",
                correo = "es@gmail.com",
                contrasena = "123456",
                empresa = "Empresa de Prueba",
                cargo = "Tester",
                depto = "QA"
            )
        }
    }

    Scaffold(
        topBar = {
            // TopBarApp (complement) integrado
            if (navController != null) {
                TopBarApp(title = "Inicio de Sesión (Login)", navController = navController)
            } else {
                TopAppBar(
                    title = { Text("Inicio de Sesión (Login)") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_empresa),
                contentDescription = "Logo de la empresa",
                modifier = Modifier
                    .size(220.dp)
                    .padding(top = 16.dp)
            )

            Text(
                text = "Bienvenido",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Correo Electrónico") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            // Botón de login
            Button(
                onClick = {
                    cargando = true
                    scope.launch {
                        val exito = viewModel.login(username, password)
                        if (exito) {
                            snackbarHostState.showSnackbar("Inicio de sesión exitoso!")
                            navController?.navigate("menu") {
                                popUpTo("login") { inclusive = true }
                                launchSingleTop = true
                            }
                        } else {
                            snackbarHostState.showSnackbar("Correo o Contraseña incorrectos")
                        }
                        cargando = false
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !cargando
            ) {
                Text("Ingresar")
            }

            // LoadingIndicator (complement) durante la carga
            if (cargando) {
                LoadingIndicator()
            }

            // Enlace a registro
            TextButton(
                onClick = { navController?.navigate("registro") },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("¿No tienes cuenta? Regístrate aquí")
            }

            // Botón Salir (muestra diálogo de confirmación)
            TextButton(
                onClick = { showExitDialog = true },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Salir")
            }
        }
    }

    // CustomDialog (complement) para confirmar salida
    CustomDialog(
        showDialog = showExitDialog,
        title = "Confirmar salida",
        message = "¿Deseas salir de la aplicación?",
        confirmText = "Sí, salir",
        dismissText = "Cancelar",
        onConfirm = {
            showExitDialog = false
            navController?.navigate("salir")
        },
        onDismiss = { showExitDialog = false }
    )
}



