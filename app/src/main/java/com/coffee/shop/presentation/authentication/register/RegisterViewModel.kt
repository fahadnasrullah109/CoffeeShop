package com.coffee.shop.presentation.authentication.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffee.shop.domain.models.ValidationResult
import com.coffee.shop.domain.usecases.ConfirmPasswordValidationUseCase
import com.coffee.shop.domain.usecases.EmailValidationUseCase
import com.coffee.shop.domain.usecases.PasswordValidationUseCase
import com.coffee.shop.domain.usecases.RegisterUseCase
import com.coffee.shop.domain.usecases.UsernameValidationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val usernameValidationUseCase: UsernameValidationUseCase,
    private val emailValidationUseCase: EmailValidationUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase,
    private val confirmPasswordValidationUseCase: ConfirmPasswordValidationUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: RegisterUIEvents) {
        viewModelScope.launch {
            when (event) {
                is RegisterUIEvents.OnRegister -> {

                    val usernameResult = usernameValidationUseCase.execute(
                        field = _uiState.value.username,
                        emptyErrorString = event.emptyUsernameError,
                        errorString = event.usernameError
                    )

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

                    val confirmPasswordResult = confirmPasswordValidationUseCase.execute(
                        field = _uiState.value.confirmPassword,
                        emptyErrorString = event.emptyConfirmPasswordError,
                        errorString = event.passwordError,
                        password = _uiState.value.password,
                        matchErrorString = event.confirmPasswordError
                    )
                    val hasError = listOf(emailResult, passwordResult).any {
                        it is ValidationResult.Error
                    }
                    if (hasError) {
                        _uiState.value = _uiState.value.copy(
                            usernameError = when (usernameResult) {
                                is ValidationResult.Success -> null
                                is ValidationResult.Error -> usernameResult.errorMessage
                            }, emailError = when (emailResult) {
                                is ValidationResult.Success -> null
                                is ValidationResult.Error -> emailResult.errorMessage
                            }, passwordError = when (passwordResult) {
                                is ValidationResult.Success -> null
                                is ValidationResult.Error -> passwordResult.errorMessage
                            }, confirmPasswordError = when (confirmPasswordResult) {
                                is ValidationResult.Success -> null
                                is ValidationResult.Error -> confirmPasswordResult.errorMessage
                            }
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            loading = true,
                            usernameError = null,
                            emailError = null,
                            passwordError = null,
                            confirmPasswordError = null
                        )
                        delay(3000)
                        _uiState.value = _uiState.value.copy(
                            loading = false, registerSuccess = true
                        )
                    }
                }

                is RegisterUIEvents.OnUsernameChanged -> _uiState.value =
                    _uiState.value.copy(email = event.username)

                is RegisterUIEvents.OnEmailChanged -> _uiState.value =
                    _uiState.value.copy(email = event.email)

                is RegisterUIEvents.OnPasswordChanged -> _uiState.value =
                    _uiState.value.copy(password = event.password)

                is RegisterUIEvents.OnConfirmPasswordChanged -> _uiState.value =
                    _uiState.value.copy(confirmPassword = event.confirmPassword)
            }
        }
    }

    private fun resetUIStates() {
        _uiState.value = _uiState.value.copy(
            usernameError = null,
            emailError = null,
            passwordError = null,
            confirmPasswordError = null,
            error = null
        )
    }
}