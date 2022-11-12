package actor

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import models.Muestra
import kotlin.coroutines.CoroutineContext
import kotlin.system.exitProcess

object Servidor : CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    private val muestras = mutableListOf<Muestra>()

    @OptIn(ObsoleteCoroutinesApi::class)
    fun peticiones() = actor<ServidorMsg> {
        for (msg in channel){
            when (msg){
                is InsertarMuestra -> {
                    if (muestras.size == 8){
                        println("Servidor lleno")
                        exitProcess(1)
                    }
                    println("Servidor recibe: ${msg.muestra}")
                    muestras.add(msg.muestra)
                }

                is ObtenerMuestra -> {
                    if (muestras.size > 0){
                        val muestra = muestras.removeAt(0)
                        println("Servidor entrega: $muestra")

                        msg.muestra.complete(muestra)
                    } else {
                        println("Servidor no tiene muestras")
                        msg.muestra.complete(null)
                    }
                }
            }
        }
    }
}