package com.develofer.opositate.presentation.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.domain.usecase.ResetPasswordUseCase
import com.develofer.opositate.presentation.viewmodel.TextFieldErrors.ValidateFieldErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ResetPasswordUiState())
    val uiState: StateFlow<ResetPasswordUiState> = _uiState.asStateFlow()

    fun onEmailChanged(newEmail: String) {
        _uiState.update { it.copy(email = newEmail) }
    }

    fun onConfirmEmailChanged(newEmail: String) {
        _uiState.update { it.copy(confirmEmail = newEmail) }
    }

    fun onEmailFocusChanged(isFocused: Boolean) {
        _uiState.update { it.copy(isEmailFocused = isFocused) }
    }

    fun onConfirmEmailFocusChanged(isFocused: Boolean) {
        _uiState.update { it.copy(isConfirmEmailFocused = isFocused) }
    }

    fun resetPassword(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        if (areFieldsValid()) {
            viewModelScope.launch {
                _uiState.update { it.copy(resetState = ResetPasswordState.Loading) }
                resetPasswordUseCase(
                    email = _uiState.value.email,
                    onSuccess = {
                        _uiState.update { it.copy(resetState = ResetPasswordState.Success) }
                        onSuccess()
                    },
                    onFailure = { errorMessage ->
                        _uiState.update { it.copy(resetState = ResetPasswordState.Failure(errorMessage)) }
                        onFailure(errorMessage)
                    }
                )
            }
        }
    }

    private fun areFieldsValid(): Boolean {
        val validatedState = validateFields(_uiState.value)
        _uiState.update { validatedState }
        return validatedState.emailValidateFieldError == ValidateFieldErrors.NONE &&
                validatedState.confirmEmailValidateFieldError == ValidateFieldErrors.NONE
    }

    private fun validateFields(state: ResetPasswordUiState): ResetPasswordUiState {
        val emailError = when {
            isFieldEmpty(state.email) -> ValidateFieldErrors.EMPTY_TEXT
            !isEmailValid(state.email) -> ValidateFieldErrors.INVALID_EMAIL
            else -> ValidateFieldErrors.NONE
        }

        val confirmEmailError = when {
            isFieldEmpty(state.confirmEmail) -> ValidateFieldErrors.EMPTY_TEXT
            state.email != state.confirmEmail -> ValidateFieldErrors.EMAILS_DO_NOT_MATCH
            !isEmailValid(state.email) -> ValidateFieldErrors.INVALID_EMAIL
            else -> ValidateFieldErrors.NONE
        }

        return state.copy(
            emailValidateFieldError = emailError,
            confirmEmailValidateFieldError = confirmEmailError
        )
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isFieldEmpty(value: String): Boolean {
        return value.isBlank()
    }
}

data class ResetPasswordUiState(
    val email: String = "",
    val confirmEmail: String = "",
    val isEmailFocused: Boolean = false,
    val isConfirmEmailFocused: Boolean = false,
    val emailValidateFieldError: ValidateFieldErrors = ValidateFieldErrors.NONE,
    val confirmEmailValidateFieldError: ValidateFieldErrors = ValidateFieldErrors.NONE,
    val resetState: ResetPasswordState = ResetPasswordState.Idle
)

sealed class ResetPasswordState {
    data object Idle : ResetPasswordState()
    data object Loading : ResetPasswordState()
    data object Success : ResetPasswordState()
    data class Failure(val error: String) : ResetPasswordState()
}

