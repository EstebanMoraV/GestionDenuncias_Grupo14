package com.example.gestiondenuncias_grupo14.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// --- Esquema de colores modo claro ---
private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    secondary = BlueLight,
    background = LightBackground,
    surface = LightSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = LightOnBackground,
    onSurface = LightOnSurface
)

// --- Esquema de colores modo oscuro ---
private val DarkColorScheme = darkColorScheme(
    primary = BlueLight,
    secondary = BluePrimary,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = DarkOnBackground,
    onSurface = DarkOnSurface
)

@Composable
fun GestionDenuncias_Grupo14Theme(
    darkTheme: Boolean = isSystemInDarkTheme(), // 👈 Detecta el modo del sistema
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
