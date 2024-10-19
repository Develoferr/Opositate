package com.develofer.opositate.presentation.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.develofer.opositate.R
import com.develofer.opositate.presentation.custom.CustomLogoImage
import com.develofer.opositate.presentation.custom.CustomLoginTextField
import com.develofer.opositate.presentation.navigation.navigateToLogin
import com.develofer.opositate.presentation.viewmodel.RegisterUiState
import com.develofer.opositate.presentation.viewmodel.RegisterViewModel
import com.develofer.opositate.ui.theme.Gray200
import com.develofer.opositate.ui.theme.OpositateTheme
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RegisterScreen(
    navController: NavHostController,
    registerViewModel: RegisterViewModel =  hiltViewModel()
) {
    val isDarkTheme = isSystemInDarkTheme()
    val isKeyboardVisible = WindowInsets.isImeVisible
    val focusManager = LocalFocusManager.current

    val uiState by registerViewModel.uiState.collectAsState()
    val dialogState = registerViewModel.getDialogState()
    var animationState: AnimationState by remember { mutableStateOf(AnimationState.Idle) }

    Box(
        modifier = Modifier
            .fillMaxSize().background(MaterialTheme.colorScheme.background)
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
    ) {
        CustomLogoImage(
            isDarkTheme = isDarkTheme,
            isKeyboardVisible = isKeyboardVisible,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        RegisterContent(
            uiState = uiState,
            isDarkTheme = isDarkTheme,
            isKeyboardVisible = isKeyboardVisible,
            registerViewModel = registerViewModel,
            navigateToLogin = { navigateToLogin(navController) },
            clearFocus = { focusManager.clearFocus() }
        )
    }
}

@Composable
private fun RegisterContent(
    uiState: RegisterUiState, isDarkTheme: Boolean, isKeyboardVisible: Boolean,
    registerViewModel: RegisterViewModel, navigateToLogin: () -> Unit, clearFocus: () -> Unit
) {
    val columnPaddingTop = if (isKeyboardVisible) 50.dp else if (isSystemInDarkTheme()) 330.dp else 240.dp
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp).padding(top = columnPaddingTop),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RegisterHeader(isDarkTheme)
        RegisterFields(uiState, registerViewModel, isDarkTheme)
        RegisterButtons(registerViewModel, navigateToLogin, clearFocus)
    }
}

@Composable
fun RegisterFields(uiState: RegisterUiState, registerViewModel: RegisterViewModel, isDarkTheme: Boolean) {

    CustomLoginTextField(
        value = uiState.username, onValueChange = { registerViewModel.onUsernameChanged(it) },
        label = stringResource(id = R.string.register_screen__label_text_field__user).uppercase(), isFocused = uiState.isUsernameFocused,
        onFocusChange = { registerViewModel.onUsernameFocusChanged(it) }, isPasswordField = false,
        supportingText = uiState.usernameValidateFieldError, isDarkTheme = isDarkTheme,
        haveToolTip = true, toolTipText = stringResource(id = R.string.register_screen__user_text_field__tool_tip)
    )

    CustomLoginTextField(
        value = uiState.email, onValueChange = { registerViewModel.onEmailChanged(it) },
        label = stringResource(id = R.string.register_screen__label_text_field__email).uppercase(), isFocused = uiState.isEmailFocused,
        onFocusChange = { registerViewModel.onEmailFocusChanged(it) }, isPasswordField = false,
        supportingText = uiState.emailValidateFieldError, isDarkTheme = isDarkTheme
    )

    CustomLoginTextField(
        value = uiState.password, onValueChange = { registerViewModel.onPasswordChanged(it) },
        label = stringResource(id = R.string.register_screen__label_text_field__password).uppercase(), isFocused = uiState.isPasswordFocused,
        onFocusChange = { registerViewModel.onPasswordFocusChanged(it) }, isPasswordField = true,
        supportingText = uiState.passwordValidateFieldError, isDarkTheme = isDarkTheme
    )
}

@Composable
fun RegisterButtons(registerViewModel: RegisterViewModel, navigateToLogin: () -> Unit, clearFocus: () -> Unit) {

    val buttonBackgroundColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else Color.Black
    Button(
        onClick = {
            registerViewModel.register()
            clearFocus()
        },
        shape = RoundedCornerShape(13.dp), modifier = Modifier.padding(top = 50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundColor)
    ) {
        Text(
            text = stringResource(id = R.string.register_screen__text_btn__register).uppercase(), fontSize = if (isSystemInDarkTheme()) 20.sp else 25.sp,
            style = MaterialTheme.typography.titleMedium, color = if (isSystemInDarkTheme()) Color.Black else Gray200,
        )
    }
    TextButton(
        onClick = { navigateToLogin() },
        modifier = Modifier.padding(vertical = 5.dp)
    ) {
        Text(
            text = stringResource(id = R.string.register_screen__text_btn__already_have_account).uppercase(), textAlign = TextAlign.Center,
            fontSize = 12.sp, style = MaterialTheme.typography.bodyMedium, letterSpacing = 0.2.sp,
            color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
            fontWeight = if (isSystemInDarkTheme()) FontWeight.Medium else FontWeight.Light, lineHeight = 14.sp,
        )
    }
}

@Composable
fun RegisterHeader(isDarkTheme: Boolean) {

    val displayText =
        if (isDarkTheme) stringResource(id = R.string.register_screen__title_text__register).uppercase()
        else stringResource(id = R.string.register_screen__title_text__register)
    Text(
        text = displayText, fontSize = if (isDarkTheme) 36.sp else 50.sp,
        color = if (isDarkTheme) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = (24).dp)
    )
    val text = stringResource(id = R.string.register_screen__text__journey_begins)
    Text(
        text = text.uppercase(Locale.getDefault()), fontSize = 13.sp, fontWeight = FontWeight.W400,
        color = if (isDarkTheme) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(top = 4.dp), letterSpacing = 3.sp
    )
    Spacer(modifier = Modifier.height(20.dp))
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun RegisterPreview() {
    OpositateTheme {
        RegisterScreen(rememberNavController())
    }
}