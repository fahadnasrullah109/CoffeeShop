package com.coffee.shop.presentation.authentication.verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coffee.shop.domain.models.ValidationResult
import com.coffee.shop.domain.usecases.OTPValidationUseCase
import com.coffee.shop.domain.usecases.VerifyOTPUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val otpValidationUseCase: OTPValidationUseCase,
    private val verifyOTPUseCase: VerifyOTPUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(VerificationUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: VerificationUIEvents) {
        viewModelScope.launch {
            when (event) {
                is VerificationUIEvents.OnConfirmOTP -> {
                    val emailResult = otpValidationUseCase.execute(
                        field = _uiState.value.otp,
                        emptyErrorString = event.emptyOTPError,
                        errorString = event.otpError
                    )
                    val hasError = listOf(emailResult).any {
                        it is ValidationResult.Error
                    }
                    if (hasError) {
                        _uiState.value = _uiState.value.copy(
                            otpError = when (emailResult) {
                                is ValidationResult.Success -> null
                                is ValidationResult.Error -> emailResult.errorMessage
                            }
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            loading = true,
                            otpError = null,
                        )
                        delay(3000)
                        _uiState.value = _uiState.value.copy(
                            loading = false, verificationSuccess = true
                        )
                    }
                }

                is VerificationUIEvents.OnOTPChanged -> _uiState.value =
                    _uiState.value.copy(otp = event.otp)

            }
        }
    }
}