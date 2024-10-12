package com.develofer.opositate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.domain.usecase.CreateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> get() = _username

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _isUsernameFocused = MutableStateFlow(false)
    val isUsernameFocused: StateFlow<Boolean> get() = _isUsernameFocused

    private val _isEmailFocused = MutableStateFlow(false)
    val isEmailFocused: StateFlow<Boolean> get() = _isEmailFocused

    private val _isPasswordFocused = MutableStateFlow(false)
    val isPasswordFocused: StateFlow<Boolean> get() = _isPasswordFocused

    var registerState: RegisterState = RegisterState.Idle
        private set

    private val _usernameError = MutableStateFlow("")
    val usernameError: StateFlow<String> = _usernameError

    private val _emailError = MutableStateFlow("")
    val emailError: StateFlow<String> = _emailError

    private val _passwordError = MutableStateFlow("")
    val passwordError: StateFlow<String> = _passwordError

    fun validateFields() {
        _usernameError.value =
            if (isFieldEmpty(username.value)) "Antes debes rellenar este campo"
            else ""

        _emailError.value = when {
            isFieldEmpty(email.value) -> "Antes debes rellenar este campo"
            !isEmailValid(email.value) -> "Debes introducir un correo válido"
            else -> ""
        }

        _passwordError.value =
            if (isFieldEmpty(password.value)) "Antes debes rellenar este campo"
            else ""
    }

    fun onUsernameChanged(newUsername: String) {
        _username.value = newUsername
    }

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun onUsernameFocusChanged(isFocused: Boolean) {
        _isUsernameFocused.value = isFocused
    }

    fun onEmailFocusChanged(isFocused: Boolean) {
        _isEmailFocused.value = isFocused
    }

    fun onPasswordFocusChanged(isFocused: Boolean) {
        _isPasswordFocused.value = isFocused
    }

    fun register(onRegisterSuccess: () -> Unit, onRegisterFailure: (String) -> Unit) {
        viewModelScope.launch {
            registerState = RegisterState.Loading
            createUserUseCase.createUser(
                username = _username.value,
                email = _email.value,
                password = _password.value,
                onSuccess = {
                    registerState = RegisterState.Success
                    onRegisterSuccess()
                },
                onFailure = { errorMessage ->
                    registerState = RegisterState.Failure(errorMessage)
                    onRegisterFailure(errorMessage)
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

    sealed class RegisterState {
        data object Idle : RegisterState()
        data object Loading : RegisterState()
        data object Success : RegisterState()
        data class Failure(val error: String) : RegisterState()
    }
}
