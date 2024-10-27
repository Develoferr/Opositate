package com.develofer.opositate.feature.login.presentation.resetpassword

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.develofer.opositate.R
import com.develofer.opositate.feature.login.presentation.component.CustomLoginTextField

@Composable
fun ResetPasswordDialog(
    resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel(),
    onSuccess: () -> Unit,
    onFailure: (errorMessage: String) -> Unit,
    onDismissRequest: () -> Unit
) {
    val uiState by resetPasswordViewModel.uiState.collectAsState()
    val isDarkTheme = isSystemInDarkTheme()

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = stringResource(id = R.string.reset_password_dialog__text__reset_password).uppercase() )
        },
        text = {
            Column {
                CustomLoginTextField(
                    value = uiState.email,
                    onValueChange = { resetPasswordViewModel.onEmailChanged(it) },
                    label = stringResource(id = R.string.reset_password_dialog__text__email).uppercase(),
                    isFocused = uiState.isEmailFocused,
                    onFocusChange = { resetPasswordViewModel.onEmailFocusChanged(it) },
                    isPasswordField = false,
                    supportingText = uiState.emailValidateFieldError,
                    isDarkTheme = isDarkTheme,
                    textLetterSpacing = 0.sp,
                    labelFontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomLoginTextField(
                    value = uiState.confirmEmail,
                    onValueChange = { resetPasswordViewModel.onConfirmEmailChanged(it) },
                    label = stringResource(id = R.string.reset_password_dialog__text__repeat_email).uppercase(),
                    isFocused = uiState.isConfirmEmailFocused,
                    onFocusChange = { resetPasswordViewModel.onConfirmEmailFocusChanged(it) },
                    isPasswordField = false,
                    supportingText = uiState.confirmEmailValidateFieldError,
                    isDarkTheme = isDarkTheme,
                    textLetterSpacing = 0.sp,
                    labelFontSize = 13.sp
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                resetPasswordViewModel.resetPassword(
                    onSuccess = { onSuccess() },
                    onFailure = { errorMessage -> onFailure(errorMessage) }
                )
            }) {
                Text(text = stringResource(id = R.string.reset_password_dialog__btn_text__send_email).uppercase())
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(id = R.string.reset_password_dialog__btn_text__cancel).uppercase())
            }
        }
    )
}

@Preview
@Composable
fun ResetPasswordDialogPreview() {
    ResetPasswordDialog(
        resetPasswordViewModel = hiltViewModel(),
        onSuccess = {},
        onFailure = {},
        onDismissRequest = {}
    )
}
