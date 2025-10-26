package com.example.gestiondenuncias_grupo14.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun QuienSomosScreen(
    navController: NavController)
{
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = androidx.compose.ui.graphics.Color.White)
            .padding(all = 16.dp),
        contentAlignment = Alignment.Center
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "¿Quienes Somos...?",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(20.dp))

                ElevatedCard (
                    modifier = Modifier.fillMaxSize()
                ){
                    Column (
                        modifier = Modifier.padding(all = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        Text(
                            text = "Somos el Mora y el Arredondo..."
                        )

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuienSomosScreenPreview() {
    val mockNavController = rememberNavController()
    // Necesitas una importación adicional para Divider si lo usas en el preview
    MaterialTheme {
        QuienSomosScreen(
            navController = mockNavController
        )
    }
}


