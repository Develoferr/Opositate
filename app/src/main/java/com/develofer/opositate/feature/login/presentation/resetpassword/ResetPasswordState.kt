package com.develofer.opositate.feature.login.presentation.resetpassword

import com.develofer.opositate.feature.login.presentation.model.TextFieldErrors.ValidateFieldErrors

data class ResetPasswordUiState(
    val email: String = "",
    val confirmEmail: String = "",
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