package com.develofer.opositate.feature.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.R
import com.develofer.opositate.feature.login.domain.usecase.LoginUseCase
import com.develofer.opositate.feature.login.domain.usecase.ResetPasswordUseCase
import com.develofer.opositate.feature.login.presentation.model.LoginUiState
import com.develofer.opositate.feature.login.presentation.model.TextFieldErrors.ValidateFieldErrors
import com.develofer.opositate.main.data.model.Result
import com.develofer.opositate.main.data.model.UiResult
import com.develofer.opositate.main.data.provider.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val resourceProvider: ResourceProvider,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChanged(newEmail: String) {
        _uiState.update { it.copy(email = newEmail) }
    }

    fun onPasswordChanged(newPassword: String) {
        _uiState.update { it.copy(password = newPassword) }
    }

    fun onEmailFocusChanged(isFocused: Boolean) {
        _uiState.update { it.copy(isEmailFocused = isFocused) }
    }

    fun onPasswordFocusChanged(isFocused: Boolean) {
        _uiState.update { it.copy(isPasswordFocused = isFocused) }
    }

    fun toggleResetPasswordDialogVisibility(show: Boolean) {
        _uiState.update { it.copy(showResetPasswordDialog = show) }
    }

    fun login() {
        if (areFieldsValid()) {
            viewModelScope.launch {
                _uiState.update { it.copy(loginState = UiResult.Loading) }
                when (val result = loginUseCase(
                    email = _uiState.value.email.trim(),
                    password = _uiState.value.password.trim()
                )) {
                    is Result.Success -> {
                        _uiState.update { it.copy(loginState = UiResult.Success) }
                    }
                    is Result.Error -> {
                        _uiState.update { it.copy(loginState = UiResult.Error(
                            result.exception.message ?: resourceProvider.getString(R.string.error_message__login_failed)))
                        }
                    }
                    is Result.Loading -> {
                        _uiState.update { it.copy(loginState = UiResult.Loading) }
                    }
                }
            }
        }
    }

    fun updatePassword(email: String) {
        _uiState.update {it.copy(updatePasswordState = UiResult.Loading)}
        viewModelScope.launch {
            when (resetPasswordUseCase(email)) {
                is Result.Success -> {
                    _uiState.update {it.copy(updatePasswordState = UiResult.Success)}
                }
                is Result.Error -> {
                    _uiState.update {it.copy(updatePasswordState = UiResult.Error("An error occurred"))}
                }

                is Result.Loading -> {}
            }
        }
    }

    private fun areFieldsValid(): Boolean {
        validateEmail()
        validatePassword()
        return _uiState.value.emailValidateFieldError == ValidateFieldErrors.NONE &&
                _uiState.value.passwordValidateFieldError == ValidateFieldErrors.NONE
    }

    fun validateEmail() {
        _uiState.update {
            it.copy(
                emailValidateFieldError = when {
                    isFieldEmpty(it.email) -> ValidateFieldErrors.EMPTY_TEXT
                    !isEmailValid(it.email) -> ValidateFieldErrors.INVALID_EMAIL
                    else -> ValidateFieldErrors.NONE
                }
            )
        }
    }

    fun validatePassword() {
        _uiState.update {
            it.copy(
                passwordValidateFieldError =
                    if (isFieldEmpty(it.password)) ValidateFieldErrors.EMPTY_TEXT
                    else ValidateFieldErrors.NONE
            )
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isFieldEmpty(value: String): Boolean {
        return value.isBlank()
    }

    fun cleanUpState() {
        _uiState.update { it.copy(
            loginState = UiResult.Idle,
            googleLoginState = UiResult.Idle,
            updatePasswordState = UiResult.Idle
        ) }
    }
}