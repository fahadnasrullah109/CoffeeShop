package com.coffee.shop.presentation.authentication.login

sealed interface LoginUIEvents {
    data class OnEmailChanged(val email: String) : LoginUIEvents
    data class OnPasswordChanged(val password: String) : LoginUIEvents
    data class OnLogin(
        val emptyEmailError: String,
        val emailError: String,
        val emptyPasswordError: String,
        val passwordError: String
    ) : LoginUIEvents
    data object ResetStates: LoginUIEvents
}