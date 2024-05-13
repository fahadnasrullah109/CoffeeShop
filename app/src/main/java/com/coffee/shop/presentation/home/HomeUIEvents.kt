package com.coffee.shop.presentation.home

sealed interface HomeUIEvents {
    data object OnSearchTapped : HomeUIEvents
    data object OnItemAddedToCart : HomeUIEvents
}