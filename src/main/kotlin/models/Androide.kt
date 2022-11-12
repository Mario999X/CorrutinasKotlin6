package models

import actor.InsertarMuestra
import actor.ServidorMsg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.delay
import kotlin.coroutines.CoroutineContext

data class Androide(
    var nombre: String,
    val maxMuestras: Int,
) : CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    suspend fun produceMuestra(servidor: SendChannel<ServidorMsg>) {
        for (i in 1..maxMuestras) {
            val muestra = Muestra(i, nombre)
            println(" ||Androide $nombre -> Muestra $muestra")
            servidor.send(InsertarMuestra(muestra))
            delay(1500)
        }
        println("\t ||Androide $nombre termino")
    }

    fun release() {
        this.job.cancel()
    }
}
