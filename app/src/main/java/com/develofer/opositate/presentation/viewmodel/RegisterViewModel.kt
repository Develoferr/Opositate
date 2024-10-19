package com.develofer.opositate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.domain.usecase.CreateUserUseCase
import com.develofer.opositate.presentation.custom.DialogStateCoordinator
import com.develofer.opositate.presentation.viewmodel.TextFieldErrors.ValidateFieldErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val registerDialogStateCoordinator: DialogStateCoordinator<RegisterDialogType> = DialogStateCoordinator()
    fun getDialogState() = registerDialogStateCoordinator.dialogState

    fun showDialog(dialogType: RegisterDialogType) {
        registerDialogStateCoordinator.showDialog(dialogType)
    }

    fun hideDialog() {
        registerDialogStateCoordinator.hideDialog()
    }

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
                _uiState.update { it.copy(registerState = RegisterState.Loading) }
                createUserUseCase.createUser(
                    username = _uiState.value.username,
                    email = _uiState.value.email,
                    password = _uiState.value.password,
                    onSuccess = {
                        _uiState.update { it.copy(registerState = RegisterState.Success) }
                        registerDialogStateCoordinator.showDialog(RegisterDialogType.REGISTER_SUCCESS)
                    },
                    onFailure = { errorMessage ->
                        _uiState.update { it.copy(registerState = RegisterState.Failure(errorMessage)) }
                        registerDialogStateCoordinator.showDialog(RegisterDialogType.REGISTER_ERROR)
                    }
                )
            }
        }
    }

    fun areFieldsValid(): Boolean {
        val validatedState = validateFields(_uiState.value)
        _uiState.update { validatedState }
        return validatedState.usernameValidateFieldError == ValidateFieldErrors.NONE &&
                validatedState.emailValidateFieldError == ValidateFieldErrors.NONE &&
                validatedState.passwordValidateFieldError == ValidateFieldErrors.NONE
    }

    private fun validateFields(state: RegisterUiState): RegisterUiState {
        val usernameError = when {
            isFieldEmpty(state.username) -> ValidateFieldErrors.EMPTY_TEXT
            else -> ValidateFieldErrors.NONE
        }

        val emailError = when {
            isFieldEmpty(state.email) -> ValidateFieldErrors.EMPTY_TEXT
            !isEmailValid(state.email) -> ValidateFieldErrors.INVALID_EMAIL
            else -> ValidateFieldErrors.NONE
        }

        val passwordError = when {
            isFieldEmpty(state.password) -> ValidateFieldErrors.EMPTY_TEXT
            else -> ValidateFieldErrors.NONE
        }

        return state.copy(
            usernameValidateFieldError = usernameError,
            emailValidateFieldError = emailError,
            passwordValidateFieldError = passwordError
        )
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isFieldEmpty(value: String): Boolean {
        return value.isBlank()
    }
}

enum class RegisterDialogType {
    REGISTER_SUCCESS,
    REGISTER_ERROR
}

data class RegisterUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
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
