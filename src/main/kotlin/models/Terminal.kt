package models

import FileController
import actor.ObtenerMuestra
import actor.ServidorMsg
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.SendChannel
import kotlin.coroutines.CoroutineContext

data class Terminal(
    val nombre: String,
    val maxMuestras: Int,
) : CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    private var muestrasTotales = 0

    private val file = FileController.init()

    suspend fun recogeMuestra(servidor: SendChannel<ServidorMsg>) {
        do {
            delay((1000..1500).random().toLong())
            // Consumimos
            val muestraAsync = CompletableDeferred<Muestra?>()
            servidor.send(ObtenerMuestra(muestraAsync))
            val muestra = muestraAsync.await()
            muestra?.let {
                muestrasTotales++
                println("------------------------------------------------")
                println("La terminal: $nombre ha recogido la muestra: $muestra ")
                println("------------------------------------------------")
                if (muestra.porcentajePureza > 60) {
                    println("-La terminal: $nombre escribiendo en fichero...")
                    file.appendText("La terminal: $nombre ha recogido la muestra: $muestra \n")

                    println("\t --Informacion agregada de la terminal: $nombre")
                }
            }
        } while (muestrasTotales < maxMuestras)
        println("\t \t -La terminal: $nombre ha llegado a su limite.")
    }

    fun release() {
        this.job.cancel()
    }

}
