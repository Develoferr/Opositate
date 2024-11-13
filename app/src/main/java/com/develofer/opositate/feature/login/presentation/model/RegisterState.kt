package com.develofer.opositate.feature.login.presentation.model

import com.develofer.opositate.feature.login.presentation.model.TextFieldErrors.ValidateFieldErrors
import com.develofer.opositate.utils.StringConstants.EMPTY_STRING

data class RegisterUiState(
    val username: String = EMPTY_STRING,
    val email: String = EMPTY_STRING,
    val password: String = EMPTY_STRING,
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