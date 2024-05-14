package com.coffee.shop.presentation.notifications

import com.coffee.shop.domain.models.DomainNotification

data class NotificationsUiState(
    val data: List<DomainNotification>? = null,
    val loading: Boolean = true,
    val error: String? = null
)