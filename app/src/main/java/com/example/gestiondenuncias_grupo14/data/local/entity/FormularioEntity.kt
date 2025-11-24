package com.example.gestiondenuncias_grupo14.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "formularios")
data class FormularioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    //  DENUNCIADO
    val denunciadoNombre: String = "",
    val denunciadoApellidoPaterno: String = "",
    val denunciadoApellidoMaterno: String = "",
    val denunciadoRut: String = "",
    val denunciadoCargo: String = "",
    val denunciadoArea: String = "",

    //  REPRESENTANTE
    val representanteNombre: String = "",
    val representanteApellidoPaterno: String = "",
    val representanteApellidoMaterno: String = "",
    val representanteRut: String = "",
    val representanteCargo: String = "",
    val representanteArea: String = "",

    //  VÍCTIMA
    val victimaNombre: String = "",
    val victimaApellidoPaterno: String = "",
    val victimaApellidoMaterno: String = "",
    val victimaRut: String = "",
    val victimaCargo: String = "",
    val victimaArea: String = "",

    //  TESTIGO
    val testigoNombre: String = "",
    val testigoApellidoPaterno: String = "",
    val testigoApellidoMaterno: String = "",
    val testigoRut: String = "",
    val testigoCargo: String = "",
    val testigoArea: String = "",

    //  TIPO DE DENUNCIA
    val tiposSeleccionados: String = "", // Se guardará como texto separado por comas
    val relacionAsimetricaVictimaDepende: Boolean = false,
    val relacionAsimetricaDenunciadoDepende: Boolean = false,
    val relacionSimetricaMismaArea: Boolean = false,
    val relacionSimetricaDistintaArea: Boolean = false,

    //  EVIDENCIA
    val evidenciaExistente: Boolean = false,
    val otrosAntecedentes: Boolean = false,
    val informadaPreviamente: Boolean = false,
    val cuentaConTestigos: Boolean = false,

    //  RELATO
    val relatoTexto: String = "",
    val relatoAudioPath: String? = null
)

