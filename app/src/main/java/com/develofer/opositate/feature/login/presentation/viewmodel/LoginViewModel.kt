package com.develofer.opositate.feature.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.feature.login.domain.usecase.LoginUseCase
import com.develofer.opositate.feature.login.presentation.model.LoginDialogType
import com.develofer.opositate.feature.login.presentation.model.LoginState
import com.develofer.opositate.feature.login.presentation.model.LoginUiState
import com.develofer.opositate.feature.login.presentation.model.TextFieldErrors.ValidateFieldErrors
import com.develofer.opositate.main.coordinator.DialogStateCoordinator
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

    private val loginDialogStateCoordinator: DialogStateCoordinator<LoginDialogType> = DialogStateCoordinator()
    fun getDialogState() = loginDialogStateCoordinator.dialogState

    fun showDialog(dialogType: LoginDialogType) {
        loginDialogStateCoordinator.showDialog(dialogType)
    }

    fun hideDialog() {
        loginDialogStateCoordinator.hideDialog()
    }

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
                _uiState.update { it.copy(loginState = LoginState.Loading) }
                loginUseCase.login(
                    email = _uiState.value.email.trim(),
                    password = _uiState.value.password.trim(),
                    onSuccess = {
                        _uiState.update { it.copy(loginState = LoginState.Success) }
                        loginDialogStateCoordinator.showDialog(LoginDialogType.LOGIN_SUCCESS)
                    },
                    onFailure = { errorMessage ->
                        _uiState.update { it.copy(loginState = LoginState.Failure(errorMessage)) }
                        loginDialogStateCoordinator.showDialog(LoginDialogType.LOGIN_ERROR)
                    }
                )
            }
        }
    }

    fun areFieldsValid(): Boolean {
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
}