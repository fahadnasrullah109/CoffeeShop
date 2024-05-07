package com.coffee.shop.presentation.authentication.forgotpassword

import com.coffee.shop.components.UiText

data class ForgotPasswordUiState(
    val email: String = "",
    val emailError: UiText? = null,
    val loading: Boolean = false,
    val emailSentSuccess: Boolean = false,
    val error: String? = null
)