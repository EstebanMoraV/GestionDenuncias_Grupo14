package com.example.gestiondenuncias_grupo14.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gestiondenuncias_grupo14.ui.screen.Login
import com.example.gestiondenuncias_grupo14.ui.screen.Registro
import com.example.gestiondenuncias_grupo14.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    usuarioViewModel: UsuarioViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // Pantalla Login
        composable("login") {
            Login(navController = navController, viewModel = usuarioViewModel)
        }

        // Pantalla Registro
        composable("registro") {
            Registro(navController = navController, viewModel = usuarioViewModel)
        }
    }
}
