package com.develofer.opositate.feature.profile.presentation.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.develofer.opositate.feature.login.presentation.component.CustomLoginTextField
import com.develofer.opositate.feature.login.presentation.model.TextFieldErrors.ValidateFieldErrors
import com.develofer.opositate.feature.profile.presentation.viewmodel.ConfirmDialogViewModel

enum class FieldValidationType {
    EMAIL,
    TEXT,
    PASSWORD,
    NONE
}

@Composable
fun ConfirmDialog(
    viewModel: ConfirmDialogViewModel = hiltViewModel(),
    title: String,
    firstFieldLabel: String,
    secondFieldLabel: String,
    confirmButtonText: String,
    cancelButtonText: String,
    firstFieldType: FieldValidationType = FieldValidationType.NONE,
    secondFieldType: FieldValidationType = FieldValidationType.NONE,
    onConfirm: (String) -> Unit,
    onCancel: () -> Unit,
    onDismissRequest: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val isDarkTheme = isSystemInDarkTheme()

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = title.uppercase())
        },
        text = {
            Column {
                CustomLoginTextField(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    value = uiState.firstFieldValue,
                    onValueChange = { viewModel.onFirstFieldChanged(it) },
                    label = firstFieldLabel.uppercase(),
                    isFocused = uiState.isFirstFieldFocused,
                    onFocusChange = { viewModel.onFirstFieldFocusChanged(it) },
                    isPasswordField = firstFieldType == FieldValidationType.PASSWORD,
                    supportingText = uiState.firstFieldValidationError,
                    isDarkTheme = isDarkTheme,
                    textLetterSpacing = 0.sp,
                    labelFontSize = 13.sp,
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomLoginTextField(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    value = uiState.secondFieldValue,
                    onValueChange = { viewModel.onSecondFieldChanged(it) },
                    label = secondFieldLabel.uppercase(),
                    isFocused = uiState.isSecondFieldFocused,
                    onFocusChange = { viewModel.onSecondFieldFocusChanged(it) },
                    isPasswordField = secondFieldType == FieldValidationType.PASSWORD,
                    supportingText = uiState.secondFieldValidationError,
                    isDarkTheme = isDarkTheme,
                    textLetterSpacing = 0.sp,
                    labelFontSize = 13.sp,
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.validateFields(
                        firstFieldType,
                        secondFieldType,
                        shouldValidateSecondField = true
                    )

                    if (uiState.firstFieldValidationError == ValidateFieldErrors.NONE &&
                        uiState.secondFieldValidationError == ValidateFieldErrors.NONE
                    ) {
                        onConfirm(
                            uiState.firstFieldValue.trim(),
                        )
                    }
                }
            ) {
                Text(text = confirmButtonText.uppercase())
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(text = cancelButtonText.uppercase())
            }
        }
    )
}

data class GenericDialogUiState(
    val firstFieldValue: String = "",
    val secondFieldValue: String = "",
    val isFirstFieldFocused: Boolean = false,
    val isSecondFieldFocused: Boolean = false,
    val firstFieldValidationError: ValidateFieldErrors = ValidateFieldErrors.NONE,
    val secondFieldValidationError: ValidateFieldErrors = ValidateFieldErrors.NONE,
    val isPasswordVisible: Boolean = false,
    val actionState: ActionState = ActionState.Idle
)

sealed class ActionState {
    data object Idle : ActionState()
    data object Loading : ActionState()
    data object Success : ActionState()
    data class Failure(val error: String) : ActionState()
}