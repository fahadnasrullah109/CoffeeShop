package com.coffee.shop.presentation.orders.history

sealed interface OrdersUIEvents {
    data object OnReorder : OrdersUIEvents
}