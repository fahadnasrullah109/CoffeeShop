package com.coffee.shop.domain.usecases

import com.coffee.shop.components.UiText
import com.coffee.shop.domain.models.ValidationResult
import com.coffee.shop.utils.AppConstant
import javax.inject.Inject

class PasswordValidationUseCase @Inject constructor() {

    fun execute(field: String, emptyErrorString: String, errorString: String): ValidationResult =
        when {
            isBlank(field) -> ValidationResult.Error(errorMessage = UiText.Dynamic(emptyErrorString))
            isValidLength(field).not() -> ValidationResult.Error(
                errorMessage = UiText.Dynamic(
                    errorString
                )
            )

            else -> ValidationResult.Success
        }

    private fun isBlank(password: String) = password.isBlank()

    private fun isValidLength(password: String) = password.length >= AppConstant.validPasswordLength
}