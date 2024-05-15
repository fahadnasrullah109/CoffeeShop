package com.coffee.shop.presentation.favorites

sealed interface FavouritesUIEvents {
    data object LoadData : FavouritesUIEvents
}