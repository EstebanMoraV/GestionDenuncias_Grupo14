package com.example.gestiondenuncias_grupo14.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestiondenuncias_grupo14.R
import com.example.gestiondenuncias_grupo14.model.Usuario
import com.example.gestiondenuncias_grupo14.viewmodel.UsuarioViewModel

// Modelo para representar cada botón con imagen y texto
data class BotonDenuncia(val imagen: Int, val descripcion: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuPrincipal(viewModel: UsuarioViewModel) {
    val usuario = viewModel.usuarioActual
    val nombre = usuario?.nombre ?: "Invitado"
    val empresa = usuario?.empresa ?: "Sin empresa"

    val fondoColor = when (empresa) {
        "Empresa A" -> MaterialTheme.colorScheme.primaryContainer
        "Empresa B" -> MaterialTheme.colorScheme.tertiaryContainer
        "Empresa C" -> MaterialTheme.colorScheme.secondaryContainer
        else -> MaterialTheme.colorScheme.background
    }

    val botones = listOf(
        BotonDenuncia(R.drawable.acoso_laboral, "Acoso Laboral"),
        BotonDenuncia(R.drawable.acososexual, "Acoso Sexual"),
        BotonDenuncia(R.drawable.violenciatrabajo, "Violencia en el Trabajo"),
        BotonDenuncia(R.drawable.otro, "Otro")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Menú Principal") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(fondoColor)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hola, $nombre",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(botones) { boton ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .padding(8.dp)
                            .size(140.dp)
                    ) {
                        Button(
                            onClick = { /* Acción futura */ },
                            modifier = Modifier.fillMaxSize(),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = boton.imagen),
                                    contentDescription = boton.descripcion,
                                    modifier = Modifier.size(64.dp),
                                    contentScale = ContentScale.Fit
                                )
                                Text(
                                    text = boton.descripcion,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun PreviewMenuPrincipal() {
    // Simulamos un ViewModel con un usuario de ejemplo
    val viewModel = UsuarioViewModel().apply {
        registrarUsuario(
            rut = "12345678-9",
            nombre = "Juan",
            apellido = "Pérez",
            correo = "juan@example.com",
            contrasena = "1234",
            empresa = "Empresa A",
            cargo = "Analista",
            depto = "Recursos Humanos"
        )
        login("juan@example.com", "1234")
    }

    MaterialTheme {
        MenuPrincipal(viewModel = viewModel)
    }
}
