package com.coffee.shop.domain.usecases

import android.util.Patterns
import com.coffee.shop.components.UiText
import com.coffee.shop.domain.models.ValidationResult
import javax.inject.Inject

class EmailValidationUseCase @Inject constructor() {

    fun execute(field: String, emptyErrorString: String, errorString: String): ValidationResult =
        when {
            isBlank(field) -> ValidationResult.Error(errorMessage = UiText.Dynamic(emptyErrorString))
            isValidEmail(field).not() -> ValidationResult.Error(
                errorMessage = UiText.Dynamic(
                    errorString
                )
            )

            else -> ValidationResult.Success
        }

    private fun isBlank(email: String) = email.isBlank()

    private fun isValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
}