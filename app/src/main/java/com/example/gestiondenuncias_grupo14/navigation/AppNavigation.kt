package com.example.gestiondenuncias_grupo14.navigation


import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gestiondenuncias_grupo14.ui.screen.DenunciadoScreen
import com.example.gestiondenuncias_grupo14.ui.screen.EvidenciaScreen
import com.example.gestiondenuncias_grupo14.ui.screen.Login
import com.example.gestiondenuncias_grupo14.ui.screen.MenuPrincipal
import com.example.gestiondenuncias_grupo14.ui.screen.QuienSomosScreen
import com.example.gestiondenuncias_grupo14.ui.screen.Registro
import com.example.gestiondenuncias_grupo14.ui.screen.RepresentanteScreen
import com.example.gestiondenuncias_grupo14.ui.screen.ResumenDenunciado
import com.example.gestiondenuncias_grupo14.ui.screen.TipoDenunciaScreen
import com.example.gestiondenuncias_grupo14.ui.screen.VictimaScreen
import com.example.gestiondenuncias_grupo14.viewmodel.UsuarioViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavigation(
    navController: NavHostController,
    usuarioViewModel: UsuarioViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = "representante"
    ) {
        composable("login") {
            Login(navController = navController, viewModel = usuarioViewModel)
        }
        composable("registro") {
            Registro(navController = navController, viewModel = usuarioViewModel)
        }
        composable("menu") {
            MenuPrincipal(navController = navController, viewModel = usuarioViewModel)
        }

        composable("quiensomos") {
            QuienSomosScreen(navController = navController)
        }
        composable("resumen") {
            ResumenDenunciado(navController = navController)
        }
        composable("denunciado") {
            DenunciadoScreen(navController = navController)
        }
        composable("representante") {
            RepresentanteScreen(navController = navController)
        }
        composable("victima") {
            VictimaScreen(navController = navController)
        }
        composable("tipodenuncia") {
            TipoDenunciaScreen(navController = navController)
        }
        composable("evidencia") {
            EvidenciaScreen(navController = navController)
        }

        /*composable("testigo") {
            TestigoScreen(navController = navController)
        }

        composable("relato") {
            RelatoScreen(navController = navController)
        }*/

    }
}


