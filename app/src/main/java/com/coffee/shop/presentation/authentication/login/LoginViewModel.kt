package com.coffee.shop.presentation.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffee.shop.domain.models.ValidationResult
import com.coffee.shop.domain.usecases.EmailValidationUseCase
import com.coffee.shop.domain.usecases.LoginUseCase
import com.coffee.shop.domain.usecases.PasswordValidationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailValidationUseCase: EmailValidationUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: LoginUIEvents) {
        viewModelScope.launch {
            when (event) {
                is LoginUIEvents.OnLogin -> {
                    val emailResult = emailValidationUseCase.execute(
                        field = _uiState.value.email,
                        emptyErrorString = event.emptyEmailError,
                        errorString = event.emailError
                    )
                    val passwordResult = passwordValidationUseCase.execute(
                        field = _uiState.value.password,
                        emptyErrorString = event.emptyPasswordError,
                        errorString = event.passwordError
                    )
                    val hasError = listOf(emailResult, passwordResult).any {
                        it is ValidationResult.Error
                    }
                    if (hasError) {
                        _uiState.value = _uiState.value.copy(
                            emailError = when (emailResult) {
                                is ValidationResult.Success -> null
                                is ValidationResult.Error -> emailResult.errorMessage
                            }, passwordError = when (passwordResult) {
                                is ValidationResult.Success -> null
                                is ValidationResult.Error -> passwordResult.errorMessage
                            }
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            loading = true,
                            emailError = null, passwordError = null
                        )
                        delay(3000)
                        _uiState.value = _uiState.value.copy(
                            loading = false, loginSuccess = true
                        )
                    }
                }

                is LoginUIEvents.OnEmailChanged -> _uiState.value =
                    _uiState.value.copy(email = event.email)

                is LoginUIEvents.OnPasswordChanged -> _uiState.value =
                    _uiState.value.copy(password = event.password)

                is LoginUIEvents.ResetStates ->
                    resetUIStates()
            }
        }
    }

    private fun resetUIStates() {
        _uiState.value = _uiState.value.copy(emailError = null, passwordError = null, error = null)
    }
}