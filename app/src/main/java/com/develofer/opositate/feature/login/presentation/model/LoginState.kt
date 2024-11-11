package com.develofer.opositate.feature.login.presentation.model

import com.develofer.opositate.feature.login.presentation.model.TextFieldErrors.ValidateFieldErrors

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isEmailFocused: Boolean = false,
    val isPasswordFocused: Boolean = false,
    val emailValidateFieldError: ValidateFieldErrors = ValidateFieldErrors.NONE,
    val passwordValidateFieldError: ValidateFieldErrors = ValidateFieldErrors.NONE,
    val showResetPasswordDialog: Boolean = false,
    val loginState: LoginState = LoginState.Idle,
)

sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data object Success : LoginState()
    data class Failure(val error: String) : LoginState()
}