package actor

import kotlinx.coroutines.CompletableDeferred
import models.Muestra

sealed class ServidorMsg

class InsertarMuestra(val muestra: Muestra) : ServidorMsg()
class ObtenerMuestra(val muestra: CompletableDeferred<Muestra?>) : ServidorMsg()

