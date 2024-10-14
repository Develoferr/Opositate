package com.develofer.opositate.presentation.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.develofer.opositate.presentation.navigation.AppRoutes
import com.develofer.opositate.presentation.navigation.navigateToHome
import com.develofer.opositate.presentation.viewmodel.LoginViewModel
import com.develofer.opositate.presentation.viewmodel.MainViewModel
import com.develofer.opositate.presentation.viewmodel.TextFieldErrors.ValidateFieldErrors
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
    val username by loginViewModel.username.collectAsState("")
    val password by loginViewModel.password.collectAsState("")
    val isUsernameFocused by loginViewModel.isUsernameFocused.collectAsState()
    val isPasswordFocused by loginViewModel.isPasswordFocused.collectAsState()
    val focusManager = LocalFocusManager.current
    val usernameValidateFieldError by loginViewModel.usernameValidateFieldError.collectAsState(ValidateFieldErrors.NONE)
    val passwordValidateFieldError by loginViewModel.passwordValidateFieldError.collectAsState(ValidateFieldErrors.NONE)

    mainViewModel.hideSystemUI()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        val isKeyboardVisible = WindowInsets.isImeVisible
        val logoAlphaLight = if (isKeyboardVisible) 0f else 1f
        val logoAlphaDark = if (isKeyboardVisible) 0f else .19f
        LogoImage(modifier = Modifier.align(Alignment.TopCenter), alphaLight = logoAlphaLight, alphaDark = logoAlphaDark)

        val columnPaddingTop = if (isKeyboardVisible) 50.dp else if (isSystemInDarkTheme()) 330.dp else 240.dp
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = columnPaddingTop),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val displayText = 
                if (isSystemInDarkTheme()) stringResource(id = R.string.login_screen_welcome_text_title).uppercase()
                else stringResource(id = R.string.login_screen_welcome_text_title)
            Text(
                text = displayText,
                fontSize = if (isSystemInDarkTheme()) 36.sp else 50.sp,
                color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(top = (24).dp)
            )
            val text = stringResource(id = R.string.login_screen_sign_in_text_subtitle).uppercase()
            Text(
                text = text.uppercase(Locale.getDefault()),
                fontSize = 13.sp,
                fontWeight = FontWeight.W400,
                color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(top = 4.dp),
                letterSpacing = 2.7.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            val containerColor =
                if (isSystemInDarkTheme()) Color.Black
                else MaterialTheme.colorScheme.primary
            val indicatorColor =
                if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onBackground
            val cursorColor =
                if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onBackground

            CustomLoginTextField(
                value = username,
                onValueChange = { loginViewModel.onUsernameChanged(it) },
                label = stringResource(id = R.string.login_screen_user_label_text_field).uppercase(),
                isFocused = isUsernameFocused,
                onFocusChange = { loginViewModel.onUsernameFocusChanged(it) },
                isPasswordField = false,
                containerColor = containerColor,
                indicatorColor = indicatorColor,
                cursorColor = cursorColor,
                supportingText = usernameValidateFieldError
            )

            CustomLoginTextField(
                value = password,
                onValueChange = { loginViewModel.onPasswordChanged(it) },
                label = stringResource(id = R.string.login_screen_password_label_text_field).uppercase(),
                isFocused = isPasswordFocused,
                onFocusChange = { loginViewModel.onPasswordFocusChanged(it) },
                isPasswordField = true,
                containerColor = containerColor,
                indicatorColor = indicatorColor,
                cursorColor = cursorColor,
                supportingText = passwordValidateFieldError
            )

            TextButton(
                onClick = {},
                modifier = Modifier
                    .padding(bottom = 5.dp, start = 50.dp)
                    .align(Alignment.Start),
            ) {
                Text(
                    text = stringResource(id = R.string.login_screen_forget_your_password_text_body).uppercase(),
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                    fontWeight = if (isSystemInDarkTheme()) FontWeight.Medium else FontWeight.Light,
                    modifier = Modifier.padding(top = 0.dp),
                    letterSpacing = 0.2.sp
                )
            }

            val buttonBackgroundColor =
                if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary
                else Color.Black

            Button(
                onClick = {
                    loginViewModel.validateFields()
                    if (loginViewModel.usernameValidateFieldError.value == ValidateFieldErrors.NONE &&
                        loginViewModel.passwordValidateFieldError.value == ValidateFieldErrors.NONE
                    ) {
                        loginViewModel.login(
                            onLoginSuccess = { navigateToHome(navController) },
                            onLoginFailure = { /* Handle error */ }
                        )
                    }
                },
                shape = RoundedCornerShape(13.dp),
                modifier = Modifier.padding(top = 20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundColor)
            ) {
                Text(
                    text = stringResource(id = R.string.login_screen_go_text_btn).uppercase(),
                    fontSize = if (isSystemInDarkTheme()) 20.sp else 25.sp,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isSystemInDarkTheme()) Color.Black else Gray200,
                )
            }

            Button(
                onClick = {},
                shape = RoundedCornerShape(13.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundColor),
                modifier = Modifier
                    .padding(vertical = 15.dp),
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
                    fontSize = if (isSystemInDarkTheme()) 20.sp else 25.sp,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isSystemInDarkTheme()) Color.Black else Gray200,
                )
            }

            TextButton(
                onClick = { navController.navigate(AppRoutes.Destination.REGISTER.route) },
                modifier = Modifier.padding(vertical = 5.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.login_screen_new_user_text_btn).uppercase(),
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    letterSpacing = 0.2.sp,
                    lineHeight = 14.sp,
                    color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                    fontWeight = if (isSystemInDarkTheme()) FontWeight.Medium else FontWeight.Light
                )
            }
        }
    }

}

@Composable
fun LogoImage(modifier: Modifier, alphaLight: Float, alphaDark: Float) {
    val colorFilter = if (isSystemInDarkTheme()) null else ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
    val modifierCopy = if (!isSystemInDarkTheme()) {
        modifier
            .size(120.dp)
            .alpha(alphaLight)
            .background(Color.Transparent)
            .padding(top = 0.dp)
            .offset(y = (120).dp, x = (0).dp)
    } else {
        Modifier
            .size(550.dp)
            .alpha(alphaDark)
            .background(Color.Transparent)
            .padding(top = 0.dp)
            .offset(y = (-60).dp, x = (-100).dp)
            .graphicsLayer { rotationX = 180f }

    }
    Image(
        painter = painterResource(id = R.drawable.brain_icon__2_),
        contentDescription = stringResource(id = R.string.login_screen_brain_image_content_description),
        colorFilter = colorFilter,
        modifier = modifierCopy,
        alignment = if (isSystemInDarkTheme()) Alignment.BottomCenter else Alignment.TopCenter
    )
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoginPreview() {
    OpositateTheme {
        LoginScreen(rememberNavController())
    }
}