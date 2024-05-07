package com.coffee.shop.presentation.authentication.verification

sealed interface VerificationUIEvents {
    data class OnOTPChanged(val otp: String) : VerificationUIEvents
    data class OnConfirmOTP(
        val emptyOTPError: String,
        val otpError: String
    ) : VerificationUIEvents
}