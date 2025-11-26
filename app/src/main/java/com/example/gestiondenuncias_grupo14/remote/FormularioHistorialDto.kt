data class FormularioHistorialDto(
    val id: Int,
    // Denunciado
    val denunciadoNombre: String,
    val denunciadoApellidoPaterno: String,
    val denunciadoApellidoMaterno: String,
    val denunciadoRut: String,
    val denunciadoCargo: String,
    val denunciadoArea: String,
    // Representante
    val representanteNombre: String,
    val representanteRut: String,
    // Tipo de denuncia
    val tiposSeleccionados: String,
    // Nueva info
    val fechaCreacion: String
)
