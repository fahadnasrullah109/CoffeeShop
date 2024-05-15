package com.coffee.shop.presentation.checkout

sealed interface CheckoutUIEvents {
    data object OnCheckout : CheckoutUIEvents

}