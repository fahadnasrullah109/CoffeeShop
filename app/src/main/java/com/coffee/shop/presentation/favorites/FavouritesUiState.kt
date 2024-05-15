package com.coffee.shop.presentation.favorites

import com.coffee.shop.domain.models.DomainCoffee

data class FavouritesUiState(
    val data: List<DomainCoffee>? = null,
    val loading: Boolean = true,
    val error: String? = null
)