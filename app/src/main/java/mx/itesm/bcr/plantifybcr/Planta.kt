package mx.itesm.bcr.plantifybcr

import java.sql.Time
import java.time.LocalTime

data class Planta(
    var nombre: String="",
    var hora: String="",
    var iluminacion: String=""
    /*var foto: Int=0,
    var temperaturaIdeal: Int=0,
    var humedadIdeal: Int=0,
    var ilumincacionIdeal: Int=0*/
)
