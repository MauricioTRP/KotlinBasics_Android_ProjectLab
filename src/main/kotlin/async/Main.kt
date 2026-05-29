package async

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

fun main() {
    runBlocking {
        launch {
            println("Peleador 1: atacka con mawashi")
            llamarCompanero() // "pausar"
            println("Peleador 1: Suki")
            llamarCompanero()
            println("Peleador 1: tag")
        }
        println("Peleador 2: atacka con mawashi")
        llamarCompanero()
        println("Peleador 2: Suki")
        llamarCompanero()
        println("Peleador 2: tag")
    }
}

suspend fun llamarCompanero() {
    println("Llamando a mi compañero")
    yield()
}
