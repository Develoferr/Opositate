package com.develofer.opositate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.domain.usecase.LoginUseCase
import com.develofer.opositate.presentation.viewmodel.TextFieldErrors.ValidateFieldErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onUsernameChanged(newUsername: String) {
        _uiState.update { it.copy(username = newUsername) }
    }

    fun onPasswordChanged(newPassword: String) {
        _uiState.update { it.copy(password = newPassword) }
    }

    fun onUsernameFocusChanged(isFocused: Boolean) {
        _uiState.update { it.copy(isUsernameFocused = isFocused) }
    }

    fun onPasswordFocusChanged(isFocused: Boolean) {
        _uiState.update { it.copy(isPasswordFocused = isFocused) }
    }

    fun areFieldsValid(): Boolean {
        val validatedState = validateFields(_uiState.value)
        _uiState.update { validatedState }
        return validatedState.usernameValidateFieldError == ValidateFieldErrors.NONE &&
                validatedState.passwordValidateFieldError == ValidateFieldErrors.NONE
    }

    private fun validateFields(state: LoginUiState): LoginUiState {
        val usernameError = when {
            isFieldEmpty(state.username) -> ValidateFieldErrors.EMPTY_TEXT
            !isEmailValid(state.username) -> ValidateFieldErrors.INVALID_EMAIL
            else -> ValidateFieldErrors.NONE
        }

        val passwordError = if (isFieldEmpty(state.password)) {
            ValidateFieldErrors.EMPTY_TEXT
        } else ValidateFieldErrors.NONE

        return state.copy(
            usernameValidateFieldError = usernameError,
            passwordValidateFieldError = passwordError
        )
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isFieldEmpty(value: String): Boolean {
        return value.isBlank()
    }

    fun login(onLoginSuccess: () -> Unit, onLoginFailure: (String) -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(loginState = LoginState.Loading) }
            loginUseCase.login(
                username = _uiState.value.username,
                password = _uiState.value.password,
                onSuccess = {
                    _uiState.update { it.copy(loginState = LoginState.Success) }
                    onLoginSuccess()
                },
                onFailure = { errorMessage ->
                    _uiState.update { it.copy(loginState = LoginState.Failure(errorMessage)) }
                    onLoginFailure(errorMessage)
                }
            )
        }
    }
}

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isUsernameFocused: Boolean = false,
    val isPasswordFocused: Boolean = false,
    val usernameValidateFieldError: ValidateFieldErrors = ValidateFieldErrors.NONE,
    val passwordValidateFieldError: ValidateFieldErrors = ValidateFieldErrors.NONE,
    val loginState: LoginState = LoginState.Idle
)

sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data object Success : LoginState()
    data class Failure(val error: String) : LoginState()
}