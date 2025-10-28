package com.example.gestiondenuncias_grupo14.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gestiondenuncias_grupo14.ui.screen.DenunciadoScreen
import com.example.gestiondenuncias_grupo14.ui.screen.EvidenciaScreen
import com.example.gestiondenuncias_grupo14.ui.screen.Login
import com.example.gestiondenuncias_grupo14.ui.screen.Registro
import com.example.gestiondenuncias_grupo14.viewmodel.UsuarioViewModel
import com.example.gestiondenuncias_grupo14.ui.screen.MenuPrincipal
import com.example.gestiondenuncias_grupo14.ui.screen.QuienSomosScreen
import com.example.gestiondenuncias_grupo14.ui.screen.RelatoScreen
import com.example.gestiondenuncias_grupo14.ui.screen.RepresentanteScreen
import com.example.gestiondenuncias_grupo14.ui.screen.ResumenDenunciado
import com.example.gestiondenuncias_grupo14.ui.screen.TestigoScreen
import com.example.gestiondenuncias_grupo14.ui.screen.TipoDenunciaScreen
import com.example.gestiondenuncias_grupo14.ui.screen.VictimaScreen
import com.example.gestiondenuncias_grupo14.viewmodel.FormularioPersonaViewModel


@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavigation(
    navController: NavHostController,
    usuarioViewModel: UsuarioViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = "relato"
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
            DenunciadoScreen(navController = navController)
        }

        // Quiénes Somos
        composable("quiensomos") {
            QuienSomosScreen(navController = navController)
        }

        // Representante
        composable("representante") {
            RepresentanteScreen(navController = navController)
        }

        // Víctima
        composable("victima") {
            VictimaScreen(navController = navController)
        }

        // Tipo de Denuncia
        composable("tipodenuncia") {
            TipoDenunciaScreen(navController = navController)
        }

        // Evidencia
        composable("evidencia") {
            EvidenciaScreen(navController = navController)
        }

        // Relato
        composable("relato") {
            RelatoScreen(navController = navController)
        }

        // Resumen
        composable("resumen") {
            ResumenDenunciado(navController = navController)
        }

        // Testigo
        composable("testigo") {
            TestigoScreen(navController = navController)
        }


    }
}
