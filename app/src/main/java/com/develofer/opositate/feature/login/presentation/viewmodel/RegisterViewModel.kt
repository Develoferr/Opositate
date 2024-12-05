package com.develofer.opositate.feature.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.R
import com.develofer.opositate.feature.login.domain.usecase.CreateUserUseCase
import com.develofer.opositate.feature.login.presentation.model.RegisterUiState
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
class RegisterViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onUsernameChanged(newUsername: String) {
        _uiState.update { it.copy(username = newUsername) }
    }

    fun onEmailChanged(newEmail: String) {
        _uiState.update { it.copy(email = newEmail) }
    }

    fun onPasswordChanged(newPassword: String) {
        _uiState.update { it.copy(password = newPassword) }
    }

    fun onUsernameFocusChanged(isFocused: Boolean) {
        _uiState.update { it.copy(isUsernameFocused = isFocused) }
    }

    fun onEmailFocusChanged(isFocused: Boolean) {
        _uiState.update { it.copy(isEmailFocused = isFocused) }
    }

    fun onPasswordFocusChanged(isFocused: Boolean) {
        _uiState.update { it.copy(isPasswordFocused = isFocused) }
    }

    fun register() {
        if (areFieldsValid()) {
            viewModelScope.launch {
                _uiState.update { it.copy(registerState = UiResult.Loading) }
                when (val result = createUserUseCase(
                    username = _uiState.value.username.trim(),
                    email = _uiState.value.email.trim(),
                    password = _uiState.value.password.trim()
                )) {
                    is Result.Success -> {
                        _uiState.update { it.copy(registerState = UiResult.Success) }
                    }
                    is Result.Error -> {
                        _uiState.update { it.copy(registerState = UiResult.Error(
                            result.exception.message ?: resourceProvider.getString(R.string.error_message__register_failed)))
                        }
                    }
                    is Result.Loading -> {
                        _uiState.update { it.copy(registerState = UiResult.Loading) }
                    }
                }
            }
        }
    }

    private fun areFieldsValid(): Boolean {
        validateUsername()
        validateEmail()
        validatePassword()
        return _uiState.value.usernameValidateFieldError == ValidateFieldErrors.NONE &&
                _uiState.value.emailValidateFieldError == ValidateFieldErrors.NONE &&
                _uiState.value.passwordValidateFieldError == ValidateFieldErrors.NONE
    }

    fun validateUsername() {
        _uiState.update {
            it.copy(
                usernameValidateFieldError = when {
                    isFieldEmpty(it.username) -> {
                        if (_uiState.value.registerState is UiResult.Idle) ValidateFieldErrors.NONE
                        else ValidateFieldErrors.EMPTY_TEXT
                    }
                    else -> ValidateFieldErrors.NONE
                }
            )
        }
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
                passwordValidateFieldError = when {
                    isFieldEmpty(it.password) -> ValidateFieldErrors.EMPTY_TEXT
                    else -> ValidateFieldErrors.NONE
                }
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
        _uiState.update { it.copy(registerState = UiResult.Idle) }
    }
}