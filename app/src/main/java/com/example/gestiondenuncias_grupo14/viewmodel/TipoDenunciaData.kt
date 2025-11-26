package com.example.gestiondenuncias_grupo14.viewmodel

data class TipoDenunciaData(
    val tiposSeleccionados: List<String> = emptyList(),

    val relacionAsimetricaVictimaDepende: Boolean = false,
    val relacionAsimetricaDenunciadoDepende: Boolean = false,
    val relacionSimetricaMismaArea: Boolean = false,
    val relacionSimetricaDistintaArea: Boolean = false
)
