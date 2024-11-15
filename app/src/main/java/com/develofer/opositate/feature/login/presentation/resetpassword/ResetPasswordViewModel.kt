package com.develofer.opositate.feature.login.presentation.resetpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.R
import com.develofer.opositate.feature.login.domain.usecase.ResetPasswordUseCase
import com.develofer.opositate.feature.login.presentation.model.TextFieldErrors.ValidateFieldErrors
import com.develofer.opositate.main.data.model.Result
import com.develofer.opositate.main.data.provider.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val resourceProvider: ResourceProvider
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
                when (val result = resetPasswordUseCase(
                    email = _uiState.value.email.trim()
                )) {
                    is Result.Success -> {
                        _uiState.update { it.copy(resetState = ResetPasswordState.Success) }
                        onSuccess()
                    }
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(resetState = ResetPasswordState.Failure(
                            result.exception.message ?: resourceProvider.getString(R.string.error_message__reset_password_failed)))
                        }
                        onFailure(result.exception.message ?: resourceProvider.getString(R.string.error_message__reset_password_failed))
                    }
                    is Result.Loading -> {
                        _uiState.update { it.copy(resetState = ResetPasswordState.Loading) }
                    }
                }
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