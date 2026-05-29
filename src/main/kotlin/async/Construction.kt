package async

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.time.measureTime

sealed class Product(val description: String, val deliveryTime: Long) {
    object Windows : Product("Windows", 10)
    object Doors : Product("Doors", 3_000)
    object Bricks : Product("Bricks", 4_000)
}

suspend fun order(item: Product): Product {
    println("Ordenando ${item.description}")
    delay(item.deliveryTime)
    println("Producto ${item.description} entregado")
    return item
}

suspend fun perform(task: String, product: Product) {
    println("\nRealizando tarea: $task con ${product.description}")
    delay(1_000)
    println("Tarea $task completada\n")
}

fun main() {
    val time = measureTime {
        runBlocking {
            // Bricks
            val bricksJob = launch(Dispatchers.Default) {
                perform("Instalar ladrillos", Product.Bricks)
            }

            // Windows
            launch(Dispatchers.IO) {
                val windows = order(Product.Windows)
                bricksJob.join() // Esperar a que se instalen los ladrillos antes de instalar las ventanas
                withContext(Dispatchers.Default) {
                    perform("Instalar ventanas", windows)
                }
            }

            // Doors
            launch(Dispatchers.IO) {
                val doors = order(Product.Doors)
                bricksJob.join() // Esperar a que se instalen los ladrillos antes de instalar las puertas
                withContext(Dispatchers.Default) {
                    perform("Instalar puertas", doors)
                }
            }

            // cancel(message = "Nos arrepentimos de la construcción")
        }
    }

    println("La ejecución tomó ${time.inWholeSeconds} segundos")
}

//fun main() {
//    val time = measureTime {
//        runBlocking {
//
//            // Windows
//            launch(Dispatchers.IO) {
//                val windows = order(Product.Windows)
//                withContext(Dispatchers.Default) {
//                    perform("Instalar ventanas", windows)
//                }
//            }
//            launch (Dispatchers.Default){
//                perform("Instalar ladrillos", Product.Bricks)
//            }
//
//            // Doors
//            launch(Dispatchers.IO) {
//                val doors = order(Product.Doors)
//                withContext(Dispatchers.Default) {
//                    perform("Instalar puertas", doors)
//                }
//            }
//        }
//    }
//
//    println("La ejecución tomó ${time.inWholeSeconds} segundos")
//}

//fun main() {
//    val time = measureTime {
//        runBlocking {
//            val doors = async { order(Product.Doors) }
//            val windows = async { order(Product.Windows) }
//
//            launch(Dispatchers.Default) {
//                perform("Instalar ladrillos", Product.Bricks)
//                launch { perform("Instalar puertas", doors.await()) }
//                launch { perform("Instalar ventanas", windows.await()) }
//            }
//        }
//    }
//
//    println("La ejecución tomó ${time.inWholeSeconds} segundos")
//}

//fun main() {
//    val time = measureTime {
//        /**
//         * Performing structured concurrency with cancellation and joins
//         */
//        runBlocking {
//            val brickJob = launch(Dispatchers.Default) {
//                perform("Instalar ladrillos", Product.Bricks)
//            }
//
//            launch(Dispatchers.IO) {
//                val doors = order(Product.Doors)
//                brickJob.join() // Wait for bricks to be installed before installing doors
//                withContext(Dispatchers.Default) {
//                    perform("Instalar puertas", doors)
//                }
//            }
//
//            launch(Dispatchers.IO) {
//                val windows = order(Product.Windows)
//                brickJob.join() // Wait for bricks to be installed before installing windows
//                withContext(Dispatchers.Default) {
//                    perform("Instalar ventanas", windows)
//                }
//            }
//        }
//    }
//}