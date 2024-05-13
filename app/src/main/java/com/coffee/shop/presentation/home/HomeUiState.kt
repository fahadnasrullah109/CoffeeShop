package com.coffee.shop.presentation.home

import com.coffee.shop.domain.models.DomainHomeData
data class HomeUiState(
    val data: DomainHomeData? = null,
    val loading: Boolean = false,
    val loginSuccess: Boolean = false,
    val error: String? = null
)