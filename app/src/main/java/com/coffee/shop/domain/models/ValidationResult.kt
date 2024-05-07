package com.coffee.shop.domain.models

import com.coffee.shop.components.UiText

sealed interface ValidationResult {
    data object Success : ValidationResult
    data class Error(val errorMessage: UiText) : ValidationResult
}
