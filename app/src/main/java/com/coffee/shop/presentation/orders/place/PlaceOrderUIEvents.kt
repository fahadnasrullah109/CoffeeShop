package com.coffee.shop.presentation.orders.place

sealed interface PlaceOrderUIEvents {
    data class OnCalculateTotal(val unitPrice: Double) : PlaceOrderUIEvents

    data class OnQuantityUpdated(val quantity: Int, val unitPrice: Double) : PlaceOrderUIEvents
}