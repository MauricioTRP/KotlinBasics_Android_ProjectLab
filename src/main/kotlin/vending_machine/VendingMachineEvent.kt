package vending_machine

sealed class VendingMachineEvent {
    data class InsertCoin(val amount: Int) : VendingMachineEvent()
    data class SelectProduct(val product: Product) : VendingMachineEvent()
    object Cancel : VendingMachineEvent()
}