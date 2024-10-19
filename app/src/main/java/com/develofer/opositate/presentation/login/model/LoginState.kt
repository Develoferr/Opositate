package com.develofer.opositate.presentation.login.model

import com.develofer.opositate.presentation.login.model.TextFieldErrors.ValidateFieldErrors


data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isUsernameFocused: Boolean = false,
    val isPasswordFocused: Boolean = false,
    val usernameValidateFieldError: ValidateFieldErrors = ValidateFieldErrors.NONE,
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