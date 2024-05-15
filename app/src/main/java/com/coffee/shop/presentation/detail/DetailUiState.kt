package com.coffee.shop.presentation.detail

data class DetailUiState(
    var isFavourite: Boolean = false,
    val favouriteEventSuccess: Boolean = false,
    val unFavouriteEventSuccess: Boolean = false,
    val error: String? = null
)