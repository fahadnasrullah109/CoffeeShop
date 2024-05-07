package com.coffee.shop.presentation.authentication.register

import com.coffee.shop.components.UiText

data class RegisterUiState(
    val username: String = "",
    val usernameError: UiText? = null,
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: UiText? = null,
    val loading: Boolean = false,
    val registerSuccess: Boolean = false,
    val error: String? = null
)