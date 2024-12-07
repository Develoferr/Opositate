package com.develofer.opositate.feature.settings.presentation.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.feature.login.presentation.model.TextFieldErrors.ValidateFieldErrors
import com.develofer.opositate.feature.profile.domain.usecase.DeleteAccountUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetUserEmailUseCase
import com.develofer.opositate.feature.settings.domain.usecase.ReauthenticateUseCase
import com.develofer.opositate.main.data.model.Result
import com.develofer.opositate.main.data.model.UiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountDeletionDialogViewModel @Inject constructor(
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val reauthenticateUseCase: ReauthenticateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AccountDeletionUiState())
    val uiState: StateFlow<AccountDeletionUiState> = _uiState.asStateFlow()

    init {
        fetchUserName()
    }

    private fun fetchUserName() {
        viewModelScope.launch {
            when (val result = getUserEmailUseCase()) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            email = result.data,
                            usernameLoadingState = UiResult.Success
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(usernameLoadingState = UiResult.Error("Failed to load user email"))
                    }
                }
                is Result.Loading -> {
                    _uiState.update {
                        it.copy(usernameLoadingState = UiResult.Loading)
                    }
                }
            }
        }
    }

    fun updateConfirmationEmail(confirmationEmail: String) {
        _uiState.update {
            it.copy(
                confirmationEmail = confirmationEmail
            )
        }
    }

    fun updateConfirmationPassword(password: String) {
        _uiState.update {
            it.copy(
                confirmationPassword = password
            )
        }
    }

    fun deleteAccount() {
        if (isFieldsValid()) {
            viewModelScope.launch {
                when (reAuthTaskTask()) {
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                passwordConfirmationError = ValidateFieldErrors.WRONG_PASSWORD
                            )
                        }
                    }
                    is Result.Success -> {
                        _uiState.update { it.copy(deleteAccountResult = UiResult.Loading) }
                        when (val result = deleteAccountUseCase()) {
                            is Result.Success -> {
                                _uiState.update { it.copy(deleteAccountResult = UiResult.Success) }
                            }
                            is Result.Error -> {
                                val error = result.exception.message ?: "An error occurred"
                                _uiState.update { it.copy(deleteAccountResult = UiResult.Error(error)) }
                            }
                            is Result.Loading -> { }
                        }
                    }
                    is Result.Loading -> { }
                }
            }
        }
    }

    private suspend fun reAuthTaskTask(): Result<Unit> =
        reauthenticateUseCase(_uiState.value.confirmationEmail, _uiState.value.confirmationPassword)

    private fun validateEmail(): ValidateFieldErrors {
        val email = _uiState.value.email
        val confirmationEmail = _uiState.value.confirmationEmail
        return when {
            email.isBlank() -> ValidateFieldErrors.EMPTY_TEXT
            !isValidEmailFormat() -> ValidateFieldErrors.INVALID_EMAIL
            email != confirmationEmail -> ValidateFieldErrors.WRONG_EMAIL
            else -> ValidateFieldErrors.NONE
        }
    }

    private fun validatePassword(): ValidateFieldErrors {
        val confirmationPassword = _uiState.value.confirmationPassword
        return when {
            confirmationPassword.isBlank() -> ValidateFieldErrors.EMPTY_TEXT
            confirmationPassword.length < 6 -> ValidateFieldErrors.INVALID_PASSWORD
            else -> ValidateFieldErrors.NONE
        }
    }

    private fun isValidEmailFormat(): Boolean {
        val email = _uiState.value.email
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isFieldsValid(): Boolean {
        val emailError = validateEmail()
        val passwordError = validatePassword()

        _uiState.update {
            it.copy(
                emailConfirmationError = emailError,
                passwordConfirmationError = passwordError,
                deleteAccountResult = UiResult.Idle
            )
        }

        return emailError == ValidateFieldErrors.NONE &&
                passwordError == ValidateFieldErrors.NONE
    }

    fun resetDeleteAccountState() {
        _uiState.update {
            it.copy(
                deleteAccountResult = UiResult.Idle,
                emailConfirmationError = ValidateFieldErrors.NONE,
                passwordConfirmationError = ValidateFieldErrors.NONE
            )
        }
    }
}

data class AccountDeletionUiState(
    val email: String = "",
    val confirmationEmail: String = "",
    val confirmationPassword: String = "",
    val usernameLoadingState: UiResult = UiResult.Idle,
    val deleteAccountResult: UiResult = UiResult.Idle,
    val emailConfirmationError: ValidateFieldErrors = ValidateFieldErrors.NONE,
    val passwordConfirmationError: ValidateFieldErrors = ValidateFieldErrors.NONE,
)
