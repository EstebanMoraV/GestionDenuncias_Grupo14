package com.example.gestiondenuncias_grupo14.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestiondenuncias_grupo14.R  // Importa el recurso R para acceder a drawable
import androidx.compose.runtime.*     // <-- Necesario para remember, mutableStateOf, by
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gestiondenuncias_grupo14.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController? = null, viewModel: UsuarioViewModel = viewModel()){

    // 🔹 Variables de estado para guardar lo que el usuario escribe
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // SnackbarHostState y coroutine scope
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Inicio de Sesión (Login)") },
                colors = topAppBarColors(
                    containerColor = colorScheme.primary,
                    titleContentColor = colorScheme.onPrimary
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) } // 👈 necesario para mostrar el globo

    ) {
        innerPadding ->
        // 🔸 Column: organiza los elementos de manera vertical
        Column(
            modifier = Modifier
                .padding(innerPadding)              // Respeta el padding interno del Scaffold
                .fillMaxSize()                      // Ocupa todo el espacio disponible
                .padding(24.dp),                    // Padding uniforme alrededor
            verticalArrangement = Arrangement.spacedBy(24.dp), // Espaciado uniforme entre elementos
            horizontalAlignment = Alignment.CenterHorizontally // Centra los elementos horizontalmente
        ) {
            //Añadimos el logo
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
                label = { Text("Nombre de usuario") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            //Spacer(modifier = Modifier.height(8.dp))

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
                    val exito = viewModel.login(username, password)
                    scope.launch {
                        if (exito) {
                            snackbarHostState.showSnackbar("Inicio de sesión exitoso!")
                            navController?.navigate("menu")

                        } else {
                            snackbarHostState.showSnackbar("Correo o Contraseña incorrectos")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Ingresar") }

            TextButton(onClick = { navController?.navigate("registro") },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.Blue
                    )
                ) {
                Text("¿No tienes cuenta? Registrate aquí")
            }

        }
    }
}

//Previsualización
@Preview(showBackground = true)
@Composable
fun LoginPreview(){
    MaterialTheme {
        Login()
    }
}