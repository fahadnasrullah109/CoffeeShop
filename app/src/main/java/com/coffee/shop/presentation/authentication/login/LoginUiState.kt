package com.coffee.shop.presentation.authentication.login

import com.coffee.shop.components.UiText

data class LoginUiState(
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val loading: Boolean = false,
    val loginSuccess: Boolean = false,
    val error: String? = null
)