package vending_machine

class VendingMachineImpl(
    private val products: List<Product>
) : VendingMachine {
    private var state: State = State.IDLE
    private var balance: Int = 0 // Plata de la máquina
    private var userBalance: Int = 0 // Plata insertada por el usuario

    override fun handleEvent(input: VendingMachineEvent) {
        when(input) {
            VendingMachineEvent.Cancel -> {


                state = State.IDLE
            }
            is VendingMachineEvent.InsertCoin -> {
            }
            is VendingMachineEvent.SelectProduct -> {
                state = State.WORKING

                // Ask for product
                println("You selected: ${input.product.name} which costs ${input.product.price}")
                val product = readln()
                val selectedProduct = products.find { it.name == product }

                // Validado existencia producto
                if (selectedProduct == null) {
                    println("Product not found")
                    // TODO check branch
                    return
                }
                // Ask for money
                println("Please insert money:")
                val inputMoney = readln()
                userBalance += inputMoney.toInt()

                // if is enough, dispense product give change back
                if (userBalance >= (selectedProduct.price)) {
                    println("Dispensing ${input.product.name}")
                    // Aumento de balance
                    // Vuelto

                    state = State.IDLE
                }
                // else ask for more money of cancel
            }
        }
    }

    enum class State {
        IDLE,
        WORKING
    }

    private fun insertCoin(amount: Int) {
        userBalance += amount
    }
}