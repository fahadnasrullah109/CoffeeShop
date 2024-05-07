package com.coffee.shop.presentation.authentication.verification

import com.coffee.shop.components.UiText

data class VerificationUiState(
    val otp: String = "",
    val otpError: UiText? = null,
    val loading: Boolean = false,
    val verificationSuccess: Boolean = false,
    val error: String? = null
)