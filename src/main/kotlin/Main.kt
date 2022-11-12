import actor.Servidor
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import models.Androide
import models.Terminal

private const val MAX_PRODUCCION = 5
private const val MAX_MUESTRA_TERMINAL = 5

// Actor Shared State
fun main() = runBlocking {

    limpiarTxt()

    val androides = listOf(Androide("R2D2", MAX_PRODUCCION), Androide("BB8", MAX_PRODUCCION))

    val terminales = listOf(Terminal("Luke", MAX_MUESTRA_TERMINAL), Terminal("Leia", MAX_MUESTRA_TERMINAL))

    androides.forEach { a ->
        launch {
            a.produceMuestra(Servidor.peticiones())
            a.release()
        }
    }

    terminales.forEach { t ->
        launch {
            t.recogeMuestra(Servidor.peticiones())
            t.release()
        }
    }
}

private fun limpiarTxt() {

    val file = FileController.init()

    file.writeText("")
}