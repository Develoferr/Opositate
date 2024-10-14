package com.develofer.opositate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.domain.usecase.LoginUseCase
import com.develofer.opositate.presentation.viewmodel.TextFieldErrors.ValidateFieldErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> get() = _username

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _isUsernameFocused = MutableStateFlow(false)
    val isUsernameFocused: StateFlow<Boolean> get() = _isUsernameFocused

    private val _isPasswordFocused = MutableStateFlow(false)
    val isPasswordFocused: StateFlow<Boolean> get() = _isPasswordFocused

    var loginState: LoginState = LoginState.Idle
        private set

    private val _usernameValidateFieldError = MutableStateFlow(ValidateFieldErrors.NONE)
    val usernameValidateFieldError: StateFlow<ValidateFieldErrors> = _usernameValidateFieldError

    private val _passwordValidateFieldError = MutableStateFlow(ValidateFieldErrors.NONE)
    val passwordValidateFieldError: StateFlow<ValidateFieldErrors> = _passwordValidateFieldError

    fun validateFields() {
        _usernameValidateFieldError.value = when {
            isFieldEmpty(username.value) -> ValidateFieldErrors.EMPTY_TEXT
            !isEmailValid(username.value) -> ValidateFieldErrors.INVALID_EMAIL
            else -> ValidateFieldErrors.NONE
        }

        _passwordValidateFieldError.value = if (isFieldEmpty(password.value)) {
            ValidateFieldErrors.EMPTY_TEXT
        } else ValidateFieldErrors.NONE
    }

    fun onUsernameChanged(newUsername: String) {
        _username.value = newUsername
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun onUsernameFocusChanged(isFocused: Boolean) {
        _isUsernameFocused.value = isFocused
    }

    fun onPasswordFocusChanged(isFocused: Boolean) {
        _isPasswordFocused.value = isFocused
    }

    fun login(onLoginSuccess: () -> Unit, onLoginFailure: (String) -> Unit) {
        viewModelScope.launch {
            loginState = LoginState.Loading
            loginUseCase.login(
                username = _username.value,
                password = _password.value,
                onSuccess = {
                    loginState = LoginState.Success
                    onLoginSuccess()
                },
                onFailure = { errorMessage ->
                    loginState = LoginState.Failure(errorMessage)
                    onLoginFailure(errorMessage)
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

    sealed class LoginState {
        data object Idle : LoginState()
        data object Loading : LoginState()
        data object Success : LoginState()
        data class Failure(val error: String) : LoginState()
    }
}
