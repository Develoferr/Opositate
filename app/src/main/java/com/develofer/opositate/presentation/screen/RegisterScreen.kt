package com.develofer.opositate.presentation.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.develofer.opositate.presentation.navigation.navigateToLogin
import com.develofer.opositate.presentation.viewmodel.RegisterViewModel
import com.develofer.opositate.ui.theme.Gray200
import com.develofer.opositate.ui.theme.OpositateTheme
import java.util.Locale

@Composable
fun RegisterScreen(
    navController: NavHostController,
    registerViewModel: RegisterViewModel =  hiltViewModel()
) {
    val username by registerViewModel.username.collectAsState("")
    val email by registerViewModel.email.collectAsState("")
    val password by registerViewModel.password.collectAsState("")
    val isUsernameFocused by registerViewModel.isUsernameFocused.collectAsState()
    val isEmailFocused by registerViewModel.isEmailFocused.collectAsState()
    val isPasswordFocused by registerViewModel.isPasswordFocused.collectAsState()
    val focusManager = LocalFocusManager.current

    val usernameError by registerViewModel.usernameError.collectAsState("")
    val emailError by registerViewModel.emailError.collectAsState("")
    val passwordError by registerViewModel.passwordError.collectAsState("")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        val colorFilter = if (isSystemInDarkTheme()) null else ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
        val modifier = if (!isSystemInDarkTheme()) {
            Modifier
                .size(120.dp)
                .graphicsLayer { alpha = 1f }
                .background(Color.Transparent)
                .padding(top = 0.dp)
                .offset(y = (120).dp, x = (0).dp)
                .align(Alignment.TopCenter)
        } else {
            Modifier
                .size(550.dp)
                .graphicsLayer { alpha = .19f }
                .background(Color.Transparent)
                .padding(top = 0.dp)
                .offset(y = (-60).dp, x = (-100).dp)
                .graphicsLayer { rotationX = 180f }

        }
        Image(
            painter = painterResource(id = R.drawable.brain_icon__2_),
            contentDescription = stringResource(id = R.string.register_screen_brain_image_content_description),
            colorFilter = colorFilter,
            modifier = modifier,
            alignment = if (isSystemInDarkTheme()) Alignment.BottomCenter else Alignment.TopCenter
        )

        val paddingTop = if (isSystemInDarkTheme()) 330.dp else 240.dp
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = paddingTop),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val displayText =
                if (isSystemInDarkTheme()) stringResource(id = R.string.register_screen_register_text_title).uppercase()
            else stringResource(id = R.string.register_screen_register_text_title)
            Text(
                text = displayText,
                fontSize = if (isSystemInDarkTheme()) 36.sp else 50.sp,
                color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(top = (24).dp)
            )
            val text = stringResource(id = R.string.register_screen_journey_begins_text_subtitle)
            Text(
                text = text.uppercase(Locale.getDefault()),
                fontSize = 13.sp,
                fontWeight = FontWeight.W400,
                color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(top = 4.dp),
                letterSpacing = 3.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                    onValueChange = { registerViewModel.onUsernameChanged(it) },
                    label = stringResource(id = R.string.register_screen_user_label_text_field).uppercase(),
                    isFocused = isUsernameFocused,
                    onFocusChange = { registerViewModel.onUsernameFocusChanged(it) },
                    isPasswordField = false,
                    containerColor = containerColor,
                    indicatorColor = indicatorColor,
                    cursorColor = cursorColor,
                    supportingText = usernameError
                )

                CustomLoginTextField(
                    value = email,
                    onValueChange = { registerViewModel.onEmailChanged(it) },
                    label = stringResource(id = R.string.register_screen_email_label_text_field).uppercase(),
                    isFocused = isEmailFocused,
                    onFocusChange = { registerViewModel.onEmailFocusChanged(it) },
                    isPasswordField = false,
                    containerColor = containerColor,
                    indicatorColor = indicatorColor,
                    cursorColor = cursorColor,
                    supportingText = emailError
                )

                CustomLoginTextField(
                    value = password,
                    onValueChange = { registerViewModel.onPasswordChanged(it) },
                    label = stringResource(id = R.string.register_screen_password_label_text_field).uppercase(),
                    isFocused = isPasswordFocused,
                    onFocusChange = { registerViewModel.onPasswordFocusChanged(it) },
                    isPasswordField = true,
                    containerColor = containerColor,
                    indicatorColor = indicatorColor,
                    cursorColor = cursorColor,
                    supportingText = passwordError
                )

                val buttonBackgroundColor =
                    if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary
                    else Color.Black

                Button(
                    onClick = {
                        registerViewModel.validateFields()
                        if (registerViewModel.usernameError.value.isNotBlank() &&
                            registerViewModel.emailError.value.isNotBlank() &&
                            registerViewModel.passwordError.value.isNotBlank()) {
                                registerViewModel.register(
                                    onRegisterSuccess = {
                                        navController.navigate(AppRoutes.Destination.LOGIN.route)
                                    },
                                    onRegisterFailure = { _ ->
                                    }
                                )
                        }

                    },
                    shape = RoundedCornerShape(13.dp),
                    modifier = Modifier.padding(top = 50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundColor)
                ) {

                    Text(
                        text = stringResource(id = R.string.register_screen_register_text_btn).uppercase(),
                        fontSize = if (isSystemInDarkTheme()) 20.sp else 25.sp,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (isSystemInDarkTheme()) Color.Black else Gray200,
                    )
                }

                TextButton(
                    onClick = { navigateToLogin(navController) },
                    modifier = Modifier.padding(vertical = 5.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.register_screen_already_have_account_text_btn).uppercase(),
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

}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun RegisterPreview() {
    OpositateTheme {
        RegisterScreen(rememberNavController())
    }
}