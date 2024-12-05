package com.develofer.opositate.feature.login.presentation.model

import com.develofer.opositate.feature.login.presentation.model.TextFieldErrors.ValidateFieldErrors
import com.develofer.opositate.main.data.model.UiResult
import com.develofer.opositate.utils.StringConstants.EMPTY_STRING

data class LoginUiState(
    val email: String = EMPTY_STRING,
    val password: String = EMPTY_STRING,
    val isEmailFocused: Boolean = false,
    val isPasswordFocused: Boolean = false,
    val emailValidateFieldError: ValidateFieldErrors = ValidateFieldErrors.NONE,
    val passwordValidateFieldError: ValidateFieldErrors = ValidateFieldErrors.NONE,
    val showResetPasswordDialog: Boolean = false,
    val loginState: UiResult = UiResult.Idle,
    val googleLoginState: UiResult = UiResult.Idle,
)