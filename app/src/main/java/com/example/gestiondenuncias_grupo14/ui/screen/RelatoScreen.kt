package com.example.gestiondenuncias_grupo14.ui.screen

import android.Manifest
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gestiondenuncias_grupo14.ui.complements.TopBarApp
import com.example.gestiondenuncias_grupo14.viewmodel.FormularioGlobalViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelatoScreen(
    navController: NavController,
    globalViewModel: FormularioGlobalViewModel = viewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    // Estado para el snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    // Estados persistentes
    var texto by rememberSaveable { mutableStateOf("") }
    val maxCaracteres = 1500
    val maxTiempoGrabacion = 5 * 60 * 1000L // 5 minutos

    var grabando by rememberSaveable { mutableStateOf(false) }
    var reproduciendo by rememberSaveable { mutableStateOf(false) }
    var mostrarDialogoBorrar by rememberSaveable { mutableStateOf(false) }

    var mediaRecorder by remember { mutableStateOf<MediaRecorder?>(null) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var archivoAudio by remember { mutableStateOf<File?>(null) }

    // Animación microfono
    val colorAnimado by animateColorAsState(
        targetValue = if (grabando) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
        animationSpec = tween(600), label = "colorMic"
    )
    val infiniteTransition = rememberInfiniteTransition(label = "mic_pulse")
    val pulso by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "pulso"
    )

    // Permisos
    var tienePermiso by rememberSaveable { mutableStateOf(false) }
    val solicitarPermiso = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido -> tienePermiso = concedido }

    LaunchedEffect(Unit) {
        solicitarPermiso.launch(Manifest.permission.RECORD_AUDIO)
    }

    // Progreso reproducción
    var duracionAudio by rememberSaveable { mutableStateOf(0) }
    var posicionActual by rememberSaveable { mutableStateOf(0) }

    LaunchedEffect(reproduciendo) {
        while (reproduciendo) {
            delay(500)
            mediaPlayer?.let {
                posicionActual = it.currentPosition
                duracionAudio = it.duration
            }
        }
    }

    Scaffold(
        topBar = { TopBarApp(title = "Relato de la situación", navController = navController) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text(
                    text = "Relato de la situación o que se denuncia",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Campo de texto
                OutlinedTextField(
                    value = texto,
                    onValueChange = {
                        if (it.length <= maxCaracteres) {
                            texto = it
                            globalViewModel.guardarRelatoTexto(it)
                        }
                    },
                    label = { Text("Escribe tu relato aquí...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    supportingText = {
                        Text(
                            text = "${texto.length}/$maxCaracteres",
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    isError = texto.length >= maxCaracteres
                )

                if (texto.length >= maxCaracteres) {
                    Text(
                        text = "Máximo 1500 caracteres",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Tu relato en audio no puede superar los 5 minutos de duración.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Botón grabar
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Button(
                        onClick = {
                            if (!tienePermiso) {
                                solicitarPermiso.launch(Manifest.permission.RECORD_AUDIO)
                                return@Button
                            }

                            if (!grabando) {
                                try {
                                    val nombreArchivo = "relato_${System.currentTimeMillis()}.mp3"
                                    val directorio = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
                                    archivoAudio = File(directorio, nombreArchivo)

                                    mediaRecorder = MediaRecorder().apply {
                                        setAudioSource(MediaRecorder.AudioSource.MIC)
                                        setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                                        setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                                        setOutputFile(archivoAudio!!.absolutePath)
                                        prepare()
                                        start()
                                    }

                                    grabando = true
                                    coroutineScope.launch {
                                        delay(maxTiempoGrabacion)
                                        if (grabando) {
                                            mediaRecorder?.stop()
                                            mediaRecorder?.release()
                                            grabando = false
                                        }
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            } else {
                                try {
                                    mediaRecorder?.stop()
                                    mediaRecorder?.release()
                                    grabando = false
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        },
                        modifier = Modifier
                            .size(100.dp)
                            .graphicsLayer(
                                scaleX = if (grabando) pulso else 1f,
                                scaleY = if (grabando) pulso else 1f
                            )
                            .clip(CircleShape),
                        colors = ButtonDefaults.buttonColors(containerColor = colorAnimado)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "Grabar audio",
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

                if (grabando) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Grabando...",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Reproducción, progreso y borrar
                archivoAudio?.let { archivo ->
                    Spacer(modifier = Modifier.height(24.dp))

                    // Reproducir / pausar
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Button(
                            onClick = {
                                try {
                                    if (!reproduciendo) {
                                        mediaPlayer = MediaPlayer().apply {
                                            setDataSource(archivo.absolutePath)
                                            prepare()
                                            start()
                                            setOnCompletionListener {
                                                reproduciendo = false
                                                posicionActual = 0
                                            }
                                        }
                                        reproduciendo = true
                                    } else {
                                        mediaPlayer?.pause()
                                        reproduciendo = false
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            },
                            modifier = Modifier
                                .height(56.dp)
                                .width(220.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (reproduciendo)
                                    MaterialTheme.colorScheme.tertiary
                                else
                                    MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Icon(
                                imageVector = if (reproduciendo) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = "Reproducir audio",
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (reproduciendo) "Pausar audio" else "Reproducir audio",
                                color = Color.White
                            )
                        }
                    }

                    // Barra de progreso
                    if (duracionAudio > 0) {
                        val progreso = (posicionActual.toFloat() / duracionAudio.toFloat()).coerceIn(0f, 1f)
                        LinearProgressIndicator(
                            progress = progreso,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp, start = 32.dp, end = 32.dp),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.secondaryContainer
                        )

                        Text(
                            text = "${(posicionActual / 1000)}s / ${(duracionAudio / 1000)}s",
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 4.dp),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }

                    //  Borrar grabación
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        OutlinedButton(
                            onClick = { mostrarDialogoBorrar = true },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Borrar grabación",
                                tint = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Borrar grabación")
                        }
                    }
                }
            }

            // Finalizar formulario
            Button(
                onClick = {
                    globalViewModel.guardarRelatoAudio(archivoAudio?.absolutePath ?: "")
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Formulario completado correctamente",
                            duration = SnackbarDuration.Short
                        )
                    }
                    navController.navigate("resumen") {
                        popUpTo("relato") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Finalizar formulario",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }

    // Confirmación borrar grabación
    if (mostrarDialogoBorrar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoBorrar = false },
            title = { Text("Eliminar grabación") },
            text = { Text("¿Seguro que deseas borrar esta grabación? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = {
                    archivoAudio?.delete()
                    archivoAudio = null
                    mediaPlayer?.release()
                    reproduciendo = false
                    mostrarDialogoBorrar = false
                }) {
                    Text("Sí, borrar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoBorrar = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RelatoScreenPreview() {
    val mockNavController = rememberNavController()
    MaterialTheme {
        RelatoScreen(navController = mockNavController)
    }
}
