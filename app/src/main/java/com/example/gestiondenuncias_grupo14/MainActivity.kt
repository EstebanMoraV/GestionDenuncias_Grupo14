package com.example.gestiondenuncias_grupo14

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.rememberNavController
import com.example.gestiondenuncias_grupo14.navigation.AppNavigation
import com.example.gestiondenuncias_grupo14.viewmodel.UsuarioViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}