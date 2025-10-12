package com.example.gestiondenuncias_grupo14.ui

// 🔹 Importaciones necesarias
import androidx.compose.foundation.layout.*         // Para Column, Row, Spacer, padding, etc.
import androidx.compose.material3.*                // Para usar los componentes de Material Design 3
import androidx.compose.runtime.Composable          // Para declarar funciones de UI
import androidx.compose.ui.Alignment               // Para alinear elementos
import androidx.compose.ui.Modifier                // Para modificar tamaño, posición, etc.
import androidx.compose.ui.tooling.preview.Preview  // Para ver la previsualización
import androidx.compose.ui.unit.dp                 // Unidad de medida independiente de densidad

// Indicamos que usaremos funciones experimentales de Material 3
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    // 🔸 Scaffold: estructura base que provee áreas comunes (topBar, bottomBar, etc.)
    Scaffold(
        topBar = {
            // 🔹 Barra superior con el color del tema y un título
            TopAppBar(
                title = { Text("Gestión de Denuncias Laborales") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary, // Usa color principal del tema
                    titleContentColor = MaterialTheme.colorScheme.onPrimary // Color del texto según contraste
                )
            )
        }
    ) { innerPadding ->
        // 🔸 Column: organiza los elementos de manera vertical
        Column(
            modifier = Modifier
                .padding(innerPadding)              // Respeta el padding interno del Scaffold
                .fillMaxSize()                      // Ocupa todo el espacio disponible
                .padding(24.dp),                    // Padding uniforme alrededor
            verticalArrangement = Arrangement.spacedBy(24.dp), // Espaciado uniforme entre elementos
            horizontalAlignment = Alignment.CenterHorizontally // Centra los elementos horizontalmente
        ) {
            // 🔹 Texto de bienvenida
            Text(
                text = "Bienvenido",
                style = MaterialTheme.typography.headlineMedium, // Usa tipografía del tema
                color = MaterialTheme.colorScheme.primary        // Usa color principal del tema
            )

            // 🔹 Subtítulo
            Text(
                text = "Explora las funciones de tu aplicación",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            // 🔹 Espaciador (opcional) — deja un espacio visual entre secciones
            Spacer(modifier = Modifier.height(8.dp))

            // 🔹 Botón principal
            Button(
                onClick = { /* Acción futura: navegar o ejecutar algo */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Presioname")
            }

            // 🔹 Nuevo elemento: botón de texto (TextButton)
            TextButton(onClick = { /* Acción futura */ }) {
                Text("Más información", color = MaterialTheme.colorScheme.primary)
            }

            // 🔹 Nuevo elemento: tarjeta con información
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Denuncia reciente",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Estado: En revisión",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // 🔹 Otro ejemplo visual: botón con icono
            OutlinedButton(onClick = { /* Acción futura */ }) {
                Text("Ver denuncias anteriores")
            }
        }
    }
}

// 🔸 Previsualización para ver el diseño sin ejecutar en dispositivo físico
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    // Usamos MaterialTheme para que los colores y estilos se apliquen correctamente
    MaterialTheme {
        HomeScreen()
    }
}
