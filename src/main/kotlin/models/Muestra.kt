package models

import java.time.LocalDateTime

data class Muestra(
    private val id: Int,
    private val idAndroide: String,
    val porcentajePureza: Int = (10..80).random(),
    val fecha: String = LocalDateTime.now().toString()
) {
    override fun toString(): String {
        return "Muestra(id=$id, idAndroide='$idAndroide', porcentajePureza=$porcentajePureza, fecha='$fecha')"
    }
}
