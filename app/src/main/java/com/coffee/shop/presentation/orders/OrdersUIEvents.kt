package com.coffee.shop.presentation.orders

sealed interface OrdersUIEvents {
    data object OnReorder : OrdersUIEvents
}