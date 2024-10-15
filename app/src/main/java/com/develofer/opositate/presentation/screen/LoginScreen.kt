package com.develofer.opositate.presentation.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
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
import com.develofer.opositate.presentation.custom.CustomLoginTextField
import com.develofer.opositate.presentation.dialog.ResetPasswordDialog
import com.develofer.opositate.presentation.navigation.AppRoutes.Destination
import com.develofer.opositate.presentation.navigation.navigateToHome
import com.develofer.opositate.presentation.viewmodel.LoginUiState
import com.develofer.opositate.presentation.viewmodel.LoginViewModel
import com.develofer.opositate.presentation.viewmodel.MainViewModel
import com.develofer.opositate.ui.theme.Gray200
import com.develofer.opositate.ui.theme.OpositateTheme
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by loginViewModel.uiState.collectAsState()
    val isDarkTheme = isSystemInDarkTheme()
    val isKeyboardVisible = WindowInsets.isImeVisible
    val focusManager = LocalFocusManager.current

    mainViewModel.hideSystemUI()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        LoginLogo(isKeyboardVisible, Modifier.align(Alignment.TopCenter), isDarkTheme)
        LoginContent(
            uiState, navController, loginViewModel, isDarkTheme, isKeyboardVisible,
            onForgotPasswordClick = { loginViewModel.toggleResetPasswordDialogVisibility(true) }
        )
        LoginResetPasswordDialog(
            uiState.showResetPasswordDialog,
            hideDialog = { loginViewModel.toggleResetPasswordDialogVisibility(false) }
        )

    }
}

@Composable
fun LoginResetPasswordDialog(showResetPasswordDialog: Boolean, hideDialog: () -> Unit) {
    if (showResetPasswordDialog) {
        ResetPasswordDialog(
            onDismissRequest = { hideDialog() },
            onSuccess = {
                // Toast de exito. Se ha enviado el correo de restablecimiento de contraseña
            },
            onFailure = {
                // Toast de error. No se ha podido restablecer la contraseña. Prueba más tarde o registra ootra cuenta.
            }
        )
    }
}

@Composable
private fun LoginLogo(isKeyboardVisible: Boolean, modifier: Modifier, isDarkTheme: Boolean) {
    val logoAlphaLight = if (isKeyboardVisible) 0f else 1f
    val logoAlphaDark = if (isKeyboardVisible) 0f else .19f
    val colorFilter = if (isDarkTheme) null else ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
    val modifierCopy = if (!isDarkTheme) {
        modifier
            .size(120.dp)
            .alpha(logoAlphaLight)
            .offset(y = 120.dp, x = 0.dp)
    } else {
        Modifier
            .size(550.dp)
            .alpha(logoAlphaDark)
            .offset(y = (-60).dp, x = (-100).dp)
            .graphicsLayer { rotationX = 180f }
    }
    Image(
        painter = painterResource(id = R.drawable.brain_icon__2_),
        contentDescription = stringResource(id = R.string.login_screen_brain_image_content_description),
        colorFilter = colorFilter,
        modifier = modifierCopy.background(Color.Transparent),
        alignment = if (isDarkTheme) Alignment.BottomCenter else Alignment.TopCenter
    )
}

@Composable
private fun LoginContent(
    uiState: LoginUiState,
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    isDarkTheme: Boolean,
    isKeyBoardVisible: Boolean,
    onForgotPasswordClick: () -> Unit
) {
    val columnPaddingTop = if (isKeyBoardVisible) 50.dp else if (isDarkTheme) 330.dp else 240.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = columnPaddingTop),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginHeader(isDarkTheme)
        LoginFields(
            uiState,
            loginViewModel,
            Modifier.align(Alignment.CenterHorizontally),
            isDarkTheme,
            onForgotPasswordClick
        )
        LoginButtons(navController, loginViewModel, isDarkTheme)
    }
}

@Composable
private fun LoginHeader(isDarkTheme: Boolean) {
    val displayText = if (isDarkTheme) {
        stringResource(id = R.string.login_screen_welcome_text_title).uppercase()
    } else {
        stringResource(id = R.string.login_screen_welcome_text_title)
    }

    Text(
        text = displayText,
        fontSize = if (isDarkTheme) 36.sp else 50.sp,
        color = if (isDarkTheme) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 24.dp)
    )

    Text(
        text = stringResource(id = R.string.login_screen_sign_in_text_subtitle).uppercase(Locale.getDefault()),
        fontSize = 13.sp,
        fontWeight = FontWeight.W400,
        color = if (isDarkTheme) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier.padding(top = 4.dp),
        letterSpacing = 2.7.sp
    )

    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
private fun LoginFields(
    uiState: LoginUiState,
    loginViewModel: LoginViewModel,
    modifier: Modifier,
    isDarkTheme: Boolean,
    onForgotPasswordClick: () -> Unit
) {
    CustomLoginTextField(
        value = uiState.username,
        onValueChange = { loginViewModel.onUsernameChanged(it) },
        label = stringResource(id = R.string.login_screen_user_label_text_field).uppercase(),
        isFocused = uiState.isUsernameFocused,
        onFocusChange = { loginViewModel.onUsernameFocusChanged(it) },
        isPasswordField = false,
        supportingText = uiState.usernameValidateFieldError,
        isDarkTheme = isDarkTheme
    )

    CustomLoginTextField(
        value = uiState.password,
        onValueChange = { loginViewModel.onPasswordChanged(it) },
        label = stringResource(id = R.string.login_screen_password_label_text_field).uppercase(),
        isFocused = uiState.isPasswordFocused,
        onFocusChange = { loginViewModel.onPasswordFocusChanged(it) },
        isPasswordField = true,
        supportingText = uiState.passwordValidateFieldError,
        isDarkTheme = isDarkTheme
    )

    ForgotPasswordButton(modifier, isDarkTheme, onForgotPasswordClick)
}

@Composable
private fun LoginButtons(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    isDarkTheme: Boolean
) {
    val buttonBackgroundColor = if (isDarkTheme) MaterialTheme.colorScheme.primary else Color.Black

    LoginButton(
        onClick = {
            loginViewModel.login(
                onLoginSuccess = { navigateToHome(navController) },
                onLoginFailure = { } // Toast Error
            )
        },
        buttonBackgroundColor = buttonBackgroundColor,
        isDarkTheme
    )

    GoogleLoginButton(buttonBackgroundColor, isDarkTheme)

    GoToRegisterButton(navController, isDarkTheme)
}

@Composable
private fun ForgotPasswordButton(
    modifier: Modifier,
    isDarkTheme: Boolean,
    onForgotPasswordClick: () -> Unit
) {
    TextButton(
        onClick = { onForgotPasswordClick() },
        modifier = modifier
            .padding(bottom = 5.dp)
    ) {
        Text(
            text = stringResource(id = R.string.login_screen_forget_your_password_text_body).uppercase(),
            fontSize = 12.sp,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isDarkTheme) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
            fontWeight = if (isDarkTheme) FontWeight.Medium else FontWeight.Light,
            modifier = Modifier.padding(top = 0.dp),
            textAlign = TextAlign.Center,
            letterSpacing = 0.2.sp,
        )
    }
}

@Composable
private fun LoginButton(onClick: () -> Unit, buttonBackgroundColor: Color, isDarkTheme: Boolean) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(13.dp),
        modifier = Modifier.padding(top = 20.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundColor)
    ) {
        Text(
            text = stringResource(id = R.string.login_screen_go_text_btn).uppercase(),
            fontSize = if (isDarkTheme) 20.sp else 25.sp,
            style = MaterialTheme.typography.titleMedium,
            color = if (isDarkTheme) Color.Black else Gray200,
        )
    }
}

@Composable
private fun GoogleLoginButton(buttonBackgroundColor: Color, isDarkTheme: Boolean) {
    Button(
        onClick = {},
        shape = RoundedCornerShape(13.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundColor),
        modifier = Modifier.padding(vertical = 15.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_google_icon),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(25.dp),
        )
        Text(
            text = stringResource(id = R.string.login_screen_google_text_btn).uppercase(),
            fontSize = if (isDarkTheme) 20.sp else 25.sp,
            style = MaterialTheme.typography.titleMedium,
            color = if (isDarkTheme) Color.Black else Gray200,
        )
    }
}

@Composable
private fun GoToRegisterButton(navController: NavHostController, isDarkTheme: Boolean) {
    TextButton(
        onClick = { navController.navigate(Destination.REGISTER.route) },
        modifier = Modifier.padding(vertical = 5.dp)
    ) {
        Text(
            text = stringResource(id = R.string.login_screen_new_user_text_btn).uppercase(),
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            style = MaterialTheme.typography.bodyMedium,
            letterSpacing = 0.2.sp,
            lineHeight = 14.sp,
            color = if (isDarkTheme) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
            fontWeight = if (isDarkTheme) FontWeight.Medium else FontWeight.Light
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoginPreview() {
    OpositateTheme {
        LoginScreen(rememberNavController())
    }
}