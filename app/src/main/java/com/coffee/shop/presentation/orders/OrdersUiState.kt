package com.coffee.shop.presentation.orders

import com.coffee.shop.domain.models.DomainOrder

data class OrdersUiState(
    val data: List<DomainOrder>? = null,
    val loading: Boolean = true,
    val error: String? = null
)