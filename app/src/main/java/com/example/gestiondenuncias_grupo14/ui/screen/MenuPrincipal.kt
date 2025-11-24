package com.example.gestiondenuncias_grupo14.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.gestiondenuncias_grupo14.viewmodel.UsuarioViewModel
import com.example.gestiondenuncias_grupo14.ui.complements.TopBarApp
import java.io.File
import java.io.FileOutputStream

// Guarda el bitmap en caché y retorna su URI
fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri {
    val file = File(context.cacheDir, "foto_temp.jpg")
    FileOutputStream(file).use { out ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
    }
    return Uri.fromFile(file)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuPrincipal(
    navController: NavController? = null,
    viewModel: UsuarioViewModel
) {
    val usuario = viewModel.usuarioActual
    val nombre = usuario?.nombre ?: "Invitado"
    val empresa = usuario?.empresa ?: "Sin empresa"
    val cargo = usuario?.cargo ?: ""
    val depto = usuario?.depto ?: ""
    val correo = usuario?.correo ?: ""

    val fondoColor = when (empresa) {
        "Empresa A" -> MaterialTheme.colorScheme.primaryContainer
        "Empresa B" -> MaterialTheme.colorScheme.tertiaryContainer
        "Empresa C" -> MaterialTheme.colorScheme.secondaryContainer
        else -> MaterialTheme.colorScheme.background
    }

    val context = LocalContext.current

    // --- FIX 1: Hacer fotoUri observable para recomposición
    var fotoUri by remember { mutableStateOf<Uri?>(viewModel.fotoUri) }

    // Launcher para TakePicturePreview (no recibe parámetros)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            val uri = saveBitmapToCache(context, bitmap)
            viewModel.fotoUri = uri
            fotoUri = uri // actualizar el estado Compose
        }
    }

    Scaffold(
        topBar = {
            if (navController != null) {
                TopBarApp(title = "Menú Principal", navController = navController)
            } else {
                TopAppBar(
                    title = { Text("Menú Principal") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (fotoUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(fotoUri),
                        contentDescription = "Foto del usuario",
                        modifier = Modifier.size(64.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Column {
                    Text(
                        text = "Hola, $nombre",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (cargo.isNotBlank()) Text("Cargo: $cargo", style = MaterialTheme.typography.bodyMedium)
                    if (depto.isNotBlank()) Text("Depto: $depto", style = MaterialTheme.typography.bodyMedium)
                    if (correo.isNotBlank()) Text("Correo: $correo", style = MaterialTheme.typography.bodyMedium)
                    Text("Empresa: $empresa", style = MaterialTheme.typography.bodyMedium)
                }

                // --- FIX 2: launch(null) para contratos Void? (TakePicturePreview)
                IconButton(onClick = { launcher.launch(null) }) {
                    Icon(
                        imageVector = Icons.Filled.CameraAlt,
                        contentDescription = "Tomar foto",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Comenzar flujo de denuncia
            Button(
                onClick = { navController?.navigate("denunciado") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Comenzar Denuncia",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            // Ver historial de formularios guardados (Room)
            OutlinedButton(
                onClick = { navController?.navigate("historial") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Ver historial de formularios")
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun PreviewMenuPrincipal() {
    val viewModel = UsuarioViewModel().apply {
        registrarUsuario(
            rut = "12345678-9",
            nombre = "Juan",
            apellido = "Pérez",
            correo = "juan@example.com",
            contrasena = "123456",
            empresa = "Empresa A",
            cargo = "Analista",
            depto = "Recursos Humanos"
        )
        login("juan@example.com", "123456")
    }
    val nav = rememberNavController()
    MaterialTheme {
        MenuPrincipal(navController = nav, viewModel = viewModel)
    }
}
