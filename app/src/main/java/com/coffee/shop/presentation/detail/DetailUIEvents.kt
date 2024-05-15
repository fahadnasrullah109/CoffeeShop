package com.coffee.shop.presentation.detail

import com.coffee.shop.domain.models.DomainCoffee

sealed interface DetailUIEvents {
    data class IsFavourite(val coffeeId: String) : DetailUIEvents
    data class OnFavouriteCoffee(val coffee: DomainCoffee) : DetailUIEvents
    data class OnUnFavouriteCoffee(val coffeeId: String) : DetailUIEvents
    data object Reset : DetailUIEvents

}