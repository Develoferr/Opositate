package com.develofer.opositate.feature.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.feature.login.presentation.model.TextFieldErrors.ValidateFieldErrors
import com.develofer.opositate.feature.profile.presentation.components.ActionState
import com.develofer.opositate.feature.profile.presentation.components.FieldValidationType
import com.develofer.opositate.feature.profile.presentation.components.GenericDialogUiState
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
class ConfirmDialogViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(GenericDialogUiState())
    val uiState: StateFlow<GenericDialogUiState> = _uiState.asStateFlow()

    fun onFirstFieldChanged(newValue: String) {
        _uiState.update { it.copy(firstFieldValue = newValue) }
    }

    fun onSecondFieldChanged(newValue: String) {
        _uiState.update { it.copy(secondFieldValue = newValue) }
    }

    fun onFirstFieldFocusChanged(isFocused: Boolean) {
        _uiState.update { it.copy(isFirstFieldFocused = isFocused) }
    }

    fun onSecondFieldFocusChanged(isFocused: Boolean) {
        _uiState.update { it.copy(isSecondFieldFocused = isFocused) }
    }

    fun onPasswordVisibilityToggle() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun validateFields(
        firstFieldType: FieldValidationType,
        secondFieldType: FieldValidationType,
        shouldValidateSecondField: Boolean = true
    ) {
        val firstFieldError = validateSingleField(
            value = _uiState.value.firstFieldValue,
            validationType = firstFieldType
        )

        val secondFieldError = if (shouldValidateSecondField) {
            validateSecondField(
                firstValue = _uiState.value.firstFieldValue,
                secondValue = _uiState.value.secondFieldValue,
                firstFieldType = firstFieldType,
                secondFieldType = secondFieldType
            )
        } else ValidateFieldErrors.NONE

        _uiState.update { it.copy(
            firstFieldValidationError = firstFieldError,
            secondFieldValidationError = secondFieldError
        ) }
    }

    private fun validateSingleField(
        value: String,
        validationType: FieldValidationType
    ): ValidateFieldErrors {
        return when (validationType) {
            FieldValidationType.EMAIL -> when {
                value.isBlank() -> ValidateFieldErrors.EMPTY_TEXT
                !isEmailValid(value) -> ValidateFieldErrors.INVALID_EMAIL
                else -> ValidateFieldErrors.NONE
            }
            FieldValidationType.PASSWORD -> when {
                value.isBlank() -> ValidateFieldErrors.EMPTY_TEXT
                value.length < 8 -> ValidateFieldErrors.INVALID_PASSWORD
                else -> ValidateFieldErrors.NONE
            }
            FieldValidationType.TEXT -> when {
                value.isBlank() -> ValidateFieldErrors.EMPTY_TEXT
                else -> ValidateFieldErrors.NONE
            }
            FieldValidationType.NONE -> ValidateFieldErrors.NONE
        }
    }

    private fun validateSecondField(
        firstValue: String,
        secondValue: String,
        firstFieldType: FieldValidationType,
        secondFieldType: FieldValidationType
    ): ValidateFieldErrors {
        if (secondValue.isBlank()) return ValidateFieldErrors.EMPTY_TEXT

        return when {
            firstFieldType == FieldValidationType.EMAIL &&
                    secondFieldType == FieldValidationType.EMAIL &&
                    firstValue != secondValue -> ValidateFieldErrors.FIELDS_DO_NOT_MATCH

            firstFieldType == FieldValidationType.PASSWORD &&
                    secondFieldType == FieldValidationType.PASSWORD &&
                    firstValue != secondValue -> ValidateFieldErrors.FIELDS_DO_NOT_MATCH

            else -> validateSingleField(secondValue, secondFieldType)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun performAction(
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        actionBlock: suspend () -> Result<Any>
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(actionState = ActionState.Loading) }

            when (val result = actionBlock()) {
                is Result.Success -> {
                    _uiState.update { it.copy(actionState = ActionState.Success) }
                    onSuccess()
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(actionState = ActionState.Failure(
                            result.exception.message ?: "An error occurred"
                        ))
                    }
                    onFailure(result.exception.message ?: "An error occurred")
                }
                is Result.Loading -> {
                    _uiState.update { it.copy(actionState = ActionState.Loading) }
                }
            }
        }
    }
}
