package com.example.gestiondenuncias_grupo14.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gestiondenuncias_grupo14.ui.complements.TopBarApp
import com.example.gestiondenuncias_grupo14.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuienSomosScreen(navController: NavController) {

    var animar by remember { mutableStateOf(false) }

    // Animación para el botón
    val colorAnimado by animateColorAsState(
        targetValue = if (animar) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
        animationSpec = tween(durationMillis = 800),
        label = ""
    )

    val alphaAnimada by animateFloatAsState(
        targetValue = if (animar) 1f else 0.7f,
        animationSpec = tween(durationMillis = 800),
        label = ""
    )

    Scaffold(
        topBar = {
            TopBarApp(title = "¿Quiénes Somos?", navController = navController)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Título
                Text(
                    text = "Somos Miguel y Esteban, estudiantes de Ingeniería en Informática.",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    textAlign = TextAlign.Center
                )

                // Descripción
                Text(
                    text = "Con entusiasmo y compromiso buscamos brindar apoyo a todas " +
                            "las personas que necesiten orientación en procesos de denuncias laborales. " +
                            "Nuestra meta es aportar con soluciones tecnológicas que faciliten el acceso a la " +
                            "justicia laboral y promuevan entornos de trabajo más justos.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                )


                // Fila con imágenes
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Imagen Miguel
                    Image(
                        painter = painterResource(id = R.drawable.miguel), // 👈 reemplázalo con tu imagen
                        contentDescription = "Miguel",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    // Imagen Esteban
                    Image(
                        painter = painterResource(id = R.drawable.mora), // 👈 reemplázalo con tu imagen
                        contentDescription = "Mora",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )


                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botón animado para volver atrás
                Button(
                    onClick = {
                        animar = !animar
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(55.dp)
                        .alpha(alphaAnimada),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorAnimado)
                ) {
                    Text(
                        text = "Menú Principal",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Créditos extra o frase inspiradora
                Text(
                    text = "Construyendo soluciones con empatía y tecnología.",
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuienSomosScreenPreview() {
    val mockNavController = rememberNavController()

    MaterialTheme {
        QuienSomosScreen(navController = mockNavController)
    }
}



