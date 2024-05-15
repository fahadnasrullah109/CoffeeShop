package com.coffee.shop.presentation.orders.place

data class PlaceOrderUiState(
    val deliveryAddress: String = "House No. X, DHA Phase XXX, Lahore, Pakistan",
    val totalAmount: Double = 0.0,
    val selectedQuantity: Int = 1,
    val deliveryCharges: Int = 1,
    val loading: Boolean = true,
    val error: String? = null
)