package com.develofer.opositate.feature.login.presentation.resetpassword

import com.develofer.opositate.feature.login.presentation.model.TextFieldErrors.ValidateFieldErrors
import com.develofer.opositate.utils.StringConstants.EMPTY_STRING

data class ResetPasswordUiState(
    val email: String = EMPTY_STRING,
    val confirmEmail: String = EMPTY_STRING,
    val isEmailFocused: Boolean = false,
    val isConfirmEmailFocused: Boolean = false,
    val emailValidateFieldError: ValidateFieldErrors = ValidateFieldErrors.NONE,
    val confirmEmailValidateFieldError: ValidateFieldErrors = ValidateFieldErrors.NONE,
    val resetState: ResetPasswordState = ResetPasswordState.Idle
)

sealed class ResetPasswordState {
    data object Idle : ResetPasswordState()
    data object Loading : ResetPasswordState()
    data object Success : ResetPasswordState()
    data class Failure(val error: String) : ResetPasswordState()
}