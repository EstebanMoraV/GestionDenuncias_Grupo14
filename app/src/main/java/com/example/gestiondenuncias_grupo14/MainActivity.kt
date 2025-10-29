package com.example.gestiondenuncias_grupo14

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.example.gestiondenuncias_grupo14.navigation.AppNavigation
import com.example.gestiondenuncias_grupo14.ui.theme.GestionDenuncias_Grupo14Theme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestionDenuncias_Grupo14Theme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}
