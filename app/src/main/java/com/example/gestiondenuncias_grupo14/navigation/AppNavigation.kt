package com.example.gestiondenuncias_grupo14.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gestiondenuncias_grupo14.ui.screen.DenunciadoScreen
import com.example.gestiondenuncias_grupo14.ui.screen.Login
import com.example.gestiondenuncias_grupo14.ui.screen.Registro
import com.example.gestiondenuncias_grupo14.viewmodel.UsuarioViewModel
import com.example.gestiondenuncias_grupo14.ui.screen.MenuPrincipal
import com.example.gestiondenuncias_grupo14.ui.screen.RepresentanteScreen
import com.example.gestiondenuncias_grupo14.ui.screen.ResumenDenunciado
import com.example.gestiondenuncias_grupo14.viewmodel.DenunciadoViewModel


@SuppressLint("ViewModelConstructorInComposable")
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
        // Pasar datos
        composable("menu") {
            MenuPrincipal(navController = navController , viewModel = usuarioViewModel)
        }

        composable ("denunciado") {
            DenunciadoScreen()
        }

        composable(route = "denunciado"){
            DenunciadoScreen(navController = navController, viewModel = DenunciadoViewModel())
        }

        composable(route = "resumen"){
            ResumenDenunciado(navController = navController, viewModel = DenunciadoViewModel())
        }

        composable(route = "representante"){
            RepresentanteScreen(navController = navController, viewModel = DenunciadoViewModel())
        }

        



    }
}
