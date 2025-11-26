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
import com.example.gestiondenuncias_grupo14.ui.screen.HistorialScreen
import com.example.gestiondenuncias_grupo14.ui.screen.Login
import com.example.gestiondenuncias_grupo14.ui.screen.Registro
import com.example.gestiondenuncias_grupo14.viewmodel.UsuarioViewModel
import com.example.gestiondenuncias_grupo14.ui.screen.MenuPrincipal
import com.example.gestiondenuncias_grupo14.ui.screen.QuienSomosScreen
import com.example.gestiondenuncias_grupo14.ui.screen.RelatoScreen
import com.example.gestiondenuncias_grupo14.ui.screen.RepresentanteScreen
import com.example.gestiondenuncias_grupo14.ui.screen.ResumenScreen
import com.example.gestiondenuncias_grupo14.ui.screen.TestigoScreen
import com.example.gestiondenuncias_grupo14.ui.screen.TipoDenunciaScreen
import com.example.gestiondenuncias_grupo14.ui.screen.VictimaScreen
import com.example.gestiondenuncias_grupo14.viewmodel.FormularioGlobalViewModel


@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavigation(
    navController: NavHostController,
    usuarioViewModel: UsuarioViewModel = viewModel(),
    globalViewModel: FormularioGlobalViewModel = viewModel()
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

        composable("denunciado") {
            DenunciadoScreen(navController = navController, globalViewModel = globalViewModel)
        }

        composable("representante") {
            RepresentanteScreen(navController = navController, globalViewModel = globalViewModel)
        }

        composable("victima") {
            VictimaScreen(navController = navController, globalViewModel = globalViewModel)
        }

        composable("tipodenuncia") {
            TipoDenunciaScreen(navController = navController, globalViewModel = globalViewModel)
        }

        composable("evidencia") {
            EvidenciaScreen(navController = navController, globalViewModel = globalViewModel)
        }

        composable("testigo") {
            TestigoScreen(navController = navController, globalViewModel = globalViewModel)
        }

        composable("relato") {
            RelatoScreen(navController = navController, globalViewModel = globalViewModel)
        }

        composable("resumen") {
            ResumenScreen(navController = navController, globalViewModel = globalViewModel)
        }

        composable("quiensomos") {
            QuienSomosScreen(navController = navController)
        }

        composable("historial") {
            HistorialScreen(navController = navController)
        }



    }
}
