package com.develofer.opositate.feature.login.presentation.model

import com.develofer.opositate.feature.login.presentation.model.TextFieldErrors.ValidateFieldErrors

data class RegisterUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val isUsernameFocused: Boolean = false,
    val isEmailFocused: Boolean = false,
    val isPasswordFocused: Boolean = false,
    val usernameValidateFieldError: ValidateFieldErrors = ValidateFieldErrors.NONE,
    val emailValidateFieldError: ValidateFieldErrors = ValidateFieldErrors.NONE,
    val passwordValidateFieldError: ValidateFieldErrors = ValidateFieldErrors.NONE,
    val registerState: RegisterState = RegisterState.Idle
)

sealed class RegisterState {
    data object Idle : RegisterState()
    data object Loading : RegisterState()
    data object Success : RegisterState()
    data class Failure(val error: String) : RegisterState()
}