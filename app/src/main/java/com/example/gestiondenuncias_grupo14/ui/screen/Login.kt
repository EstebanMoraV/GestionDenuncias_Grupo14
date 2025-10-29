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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController? = null, viewModel: UsuarioViewModel = viewModel()) {

    // Prellenado de campos
    var username by remember { mutableStateOf("es@gmail.com") }
    var password by remember { mutableStateOf("123456") }
    var cargando by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Registro automático del usuario por defecto
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
            TopAppBar(
                title = { Text("Inicio de Sesión (Login)") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
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
                    .size(280.dp)
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
                modifier = Modifier.fillMaxWidth()
            ) {
                if (cargando) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(20.dp)
                                .padding(end = 8.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                        Text("Cargando...")
                    }
                } else {
                    Text("Ingresar")
                }
            }

            TextButton(
                onClick = { navController?.navigate("registro") },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("¿No tienes cuenta? Regístrate aquí")
            }
        }
    }
}




