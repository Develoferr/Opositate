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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusManager
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
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.develofer.opositate.R
import com.develofer.opositate.presentation.custom.CustomLoginTextField
import com.develofer.opositate.presentation.custom.DialogState
import com.develofer.opositate.presentation.custom.ErrorDialog
import com.develofer.opositate.presentation.custom.SuccessDialog
import com.develofer.opositate.presentation.dialog.ResetPasswordDialog
import com.develofer.opositate.presentation.navigation.AppRoutes.Destination
import com.develofer.opositate.presentation.navigation.navigateToHome
import com.develofer.opositate.presentation.viewmodel.LoginDialogType
import com.develofer.opositate.presentation.viewmodel.LoginState
import com.develofer.opositate.presentation.viewmodel.LoginUiState
import com.develofer.opositate.presentation.viewmodel.LoginViewModel
import com.develofer.opositate.presentation.viewmodel.MainViewModel
import com.develofer.opositate.ui.theme.Gray200
import com.develofer.opositate.ui.theme.OpositateTheme
import com.develofer.opositate.utils.Constants.EMPTY_TEXT
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val isDarkTheme = isSystemInDarkTheme()
    val isKeyboardVisible = WindowInsets.isImeVisible
    val focusManager = LocalFocusManager.current

    val uiState by loginViewModel.uiState.collectAsState()
    val dialogState = loginViewModel.getDialogState()
    var passwordResetFinished by remember { mutableStateOf(false) }
    var animationState: AnimationState by remember { mutableStateOf(AnimationState.Idle) }
    var errorMessage: String? by remember { mutableStateOf(null) }

    mainViewModel.hideSystemUI()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
    ) {
        LoginLogo(
            isKeyboardVisible = isKeyboardVisible,
            isDarkTheme = isDarkTheme,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        LoginContent(
            uiState, navController, loginViewModel, isDarkTheme, isKeyboardVisible,
            onForgotPasswordClick = { loginViewModel.toggleResetPasswordDialogVisibility(true) },
            clearFocus = { focusManager.clearFocus() }
        )
        LoginResetPasswordDialog(
            showResetPasswordDialog = uiState.showResetPasswordDialog,
            focusManager = focusManager,
            loginViewModel = loginViewModel,
            hideDialog = { loginViewModel.toggleResetPasswordDialogVisibility(false) },
            { newErrorMessage -> errorMessage = newErrorMessage },
            { passwordResetFinished = true}
        )
        LoginLoadingAnimation(
            loginState = uiState.loginState,
            animationState = animationState,
            loginViewModel = loginViewModel,
            modifier = Modifier.align(Alignment.BottomCenter),
            onAnimationStateChanged = { newAnimationState -> animationState = newAnimationState }
        )
        HandleDialog(
            hideDialog = {
                animationState = AnimationState.Idle
                loginViewModel.hideDialog()
                         },
            animationState = animationState,
            uiState = uiState,
            errorMessage = errorMessage,
            dialogState = dialogState,
            passwordResetFinished = passwordResetFinished,
            onAnimationStateChanged = { newAnimationState -> animationState = newAnimationState },
            goToHome = { navigateToHome(navController) },
            onDialogDismissed = { passwordResetFinished = false }
        )
    }
}

@Composable
private fun LoginLoadingAnimation(
    loginState: LoginState, animationState: AnimationState, loginViewModel: LoginViewModel,
    modifier: Modifier, onAnimationStateChanged: (animationsState: AnimationState) -> Unit
) {
    if (loginState == LoginState.Loading || animationState == AnimationState.Loading) {
        onAnimationStateChanged(AnimationState.Loading)
        LottieLoginAnimation(
            modifier = modifier.zIndex(2f),
            loginViewModel
        ) { onAnimationStateChanged(AnimationState.Finish) }
    }
}

@Composable
fun LottieLoginAnimation(
    modifier: Modifier = Modifier, loginViewModel: LoginViewModel, onFinish: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_anim7))
    val progress by animateLottieCompositionAsState(composition = composition, iterations = 1)
    if (progress >= 1f && loginViewModel.areFieldsValid()) {
        LaunchedEffect(Unit) {
            onFinish()
        }
    }
    Surface(
        modifier = modifier.fillMaxSize().alpha(1f),
        color = MaterialTheme.colorScheme.background
    ) {
        LottieAnimation(
            composition = composition, progress = { progress },
            modifier = modifier.size(200.dp).alpha(1f).offset(y = (50).dp)
        )
    }
}


@Composable
fun LoginResetPasswordDialog(
    showResetPasswordDialog: Boolean,
    focusManager: FocusManager,
    loginViewModel: LoginViewModel,
    hideDialog: () -> Unit,
    saveErrorMessage: (String) -> Unit,
    onPasswordFinished: () -> Unit
) {
    if (showResetPasswordDialog) {
        clearFocus(focusManager, loginViewModel)
        ResetPasswordDialog(
            onDismissRequest = { hideDialog() },
            onSuccess = {
                hideDialog()
                onPasswordFinished()
                loginViewModel.showDialog(LoginDialogType.RESET_PASSWORD_SUCCESS)
            },
            onFailure = { newErrorMessage ->
                saveErrorMessage(newErrorMessage)
                hideDialog()
                onPasswordFinished()
                loginViewModel.showDialog(LoginDialogType.RESET_PASSWORD_SUCCESS)
            }
        )
    }
}

@Composable
private fun LoginLogo(
    isKeyboardVisible: Boolean, isDarkTheme: Boolean, modifier: Modifier
) {
    val logoAlphaLight = if (isKeyboardVisible) 0f else 1f
    val logoAlphaDark = if (isKeyboardVisible) 0f else .19f
    val colorFilter = if (isDarkTheme) null else ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
    val modifierCopy = if (!isDarkTheme) modifier
        .size(120.dp)
        .alpha(logoAlphaLight)
        .offset(y = 120.dp, x = 0.dp)
        else Modifier
        .size(550.dp)
        .alpha(logoAlphaDark)
        .offset(y = (-60).dp, x = (-100).dp)
        .graphicsLayer { rotationX = 180f }
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
    uiState: LoginUiState, navController: NavHostController, loginViewModel: LoginViewModel,
    isDarkTheme: Boolean, isKeyBoardVisible: Boolean, onForgotPasswordClick: () -> Unit, clearFocus: () -> Unit
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
        LoginFields(uiState, loginViewModel, Modifier.align(Alignment.CenterHorizontally), isDarkTheme, onForgotPasswordClick)
        LoginButtons(navController, loginViewModel, isDarkTheme, clearFocus)
    }
}

@Composable
private fun LoginHeader(
    isDarkTheme: Boolean
) {
    val displayText = if (isDarkTheme) stringResource(id = R.string.login_screen_welcome_text_title).uppercase()
        else stringResource(id = R.string.login_screen_welcome_text_title)
    Text(
        text = displayText, fontSize = if (isDarkTheme) 36.sp else 50.sp,
        color = if (isDarkTheme) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 24.dp)
    )
    Text(
        text = stringResource(id = R.string.login_screen_sign_in_text_subtitle).uppercase(Locale.getDefault()),
        fontSize = 13.sp, fontWeight = FontWeight.W400, style = MaterialTheme.typography.labelMedium,
        color = if (isDarkTheme) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(top = 4.dp), letterSpacing = 2.7.sp
    )
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
private fun LoginFields(
    uiState: LoginUiState, loginViewModel: LoginViewModel, modifier: Modifier,
    isDarkTheme: Boolean, onForgotPasswordClick: () -> Unit
) {
    CustomLoginTextField(
        value = uiState.username, onValueChange = { loginViewModel.onUsernameChanged(it) },
        label = stringResource(id = R.string.login_screen_user_label_text_field).uppercase(), isFocused = uiState.isUsernameFocused,
        onFocusChange = { loginViewModel.onUsernameFocusChanged(it) }, isPasswordField = false,
        supportingText = uiState.usernameValidateFieldError, isDarkTheme = isDarkTheme
    )
    CustomLoginTextField(
        value = uiState.password, onValueChange = { loginViewModel.onPasswordChanged(it) },
        label = stringResource(id = R.string.login_screen_password_label_text_field).uppercase(), isFocused = uiState.isPasswordFocused,
        onFocusChange = { loginViewModel.onPasswordFocusChanged(it) }, isPasswordField = true,
        supportingText = uiState.passwordValidateFieldError, isDarkTheme = isDarkTheme
    )
    ForgotPasswordButton(modifier, isDarkTheme, onForgotPasswordClick)
}

@Composable
private fun LoginButtons(
    navController: NavHostController, loginViewModel: LoginViewModel,
    isDarkTheme: Boolean, clearFocus: () -> Unit = {}
) {
    val buttonBackgroundColor = if (isDarkTheme) MaterialTheme.colorScheme.primary else Color.Black

    LoginButton(
        onClick = {
            loginViewModel.login()
            clearFocus()
        },
        buttonBackgroundColor = buttonBackgroundColor, isDarkTheme = isDarkTheme
    )
    GoogleLoginButton(buttonBackgroundColor, isDarkTheme)
    GoToRegisterButton(navController, isDarkTheme)
}

@Composable
private fun ForgotPasswordButton(
    modifier: Modifier, isDarkTheme: Boolean, onForgotPasswordClick: () -> Unit
) {
    TextButton(
        onClick = { onForgotPasswordClick() }, modifier = modifier.padding(bottom = 5.dp)
    ) {
        Text(
            text = stringResource(id = R.string.login_screen_forget_your_password_text_body).uppercase(), fontSize = 12.sp,
            style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center, letterSpacing = 0.2.sp,
            color = if (isDarkTheme) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
            fontWeight = if (isDarkTheme) FontWeight.Medium else FontWeight.Light, modifier = Modifier.padding(top = 0.dp)
        )
    }
}

@Composable
private fun LoginButton(
    onClick: () -> Unit, buttonBackgroundColor: Color, isDarkTheme: Boolean
) {
    Button(
        onClick = onClick, shape = RoundedCornerShape(13.dp), modifier = Modifier.padding(top = 20.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundColor)
    ) {
        Text(
            text = stringResource(id = R.string.login_screen_go_text_btn).uppercase(), fontSize = if (isDarkTheme) 20.sp else 25.sp,
            style = MaterialTheme.typography.titleMedium, color = if (isDarkTheme) Color.Black else Gray200,
        )
    }
}

@Composable
private fun GoogleLoginButton(
    buttonBackgroundColor: Color, isDarkTheme: Boolean
) {
    Button(
        onClick = {}, shape = RoundedCornerShape(13.dp),
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
private fun GoToRegisterButton(
    navController: NavHostController, isDarkTheme: Boolean
) {
    TextButton(
        onClick = { navController.navigate(Destination.REGISTER.route) },
        modifier = Modifier.padding(vertical = 5.dp)
    ) {
        Text(
            text = stringResource(id = R.string.login_screen_new_user_text_btn).uppercase(), textAlign = TextAlign.Center,
            fontSize = 12.sp, style = MaterialTheme.typography.bodyMedium, letterSpacing = 0.2.sp,
            lineHeight = 14.sp, fontWeight = if (isDarkTheme) FontWeight.Medium else FontWeight.Light,
            color = if (isDarkTheme) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
private fun HandleDialog(
    animationState: AnimationState, uiState: LoginUiState, errorMessage: String?,
    onAnimationStateChanged: (animationState: AnimationState) -> Unit,
    goToHome: () -> Unit, dialogState: StateFlow<DialogState<LoginDialogType>>,
    hideDialog: () -> Unit,
    passwordResetFinished: Boolean,
    onDialogDismissed: () -> Unit
) {
    if (animationState == AnimationState.Finish || animationState == AnimationState.Dialog ||
        passwordResetFinished) {
        onAnimationStateChanged(AnimationState.Dialog)
        if (dialogState.collectAsState().value.isVisible) {
            when (dialogState.collectAsState().value.dialogType) {
                LoginDialogType.LOGIN_SUCCESS -> {
                    SuccessDialog(
                        onDismiss = {
                            hideDialog()
                            goToHome()
                        },
                        isDialogVisible = dialogState.collectAsState().value.isVisible,
                        delayTime = 3000,
                        title = { Text(text = stringResource(id = R.string.login_screen__text__login_successful_title)) },
                        text = { Text(stringResource(id = R.string.login_screen__text__login_successful_text)) },
                        confirmButton = { TextButton(onClick = {}) { Text(EMPTY_TEXT) } },
                    )
                }
                LoginDialogType.LOGIN_ERROR -> {
                    val error: String? = if (uiState.loginState is LoginState.Failure) {
                        uiState.loginState.error
                    } else null
                    ErrorDialog(
                        title = { Text(text = stringResource(id = R.string.login_screen__text__login_error_title)) },
                        text = { Text(text = error ?: stringResource(id = R.string.login_screen__text__generic_error_text)) },
                        confirmButton = {
                            TextButton(onClick = { hideDialog() }) { Text(
                                stringResource(id = R.string.login_screen__text__ok)
                            ) }
                        },
                        onDismiss = { hideDialog() },
                        isDialogVisible = dialogState.collectAsState().value.isVisible
                    )
                }
                LoginDialogType.RESET_PASSWORD_SUCCESS -> {
                    SuccessDialog(
                        onDismiss = {
                            onDialogDismissed()
                            hideDialog()
                                    },
                        isDialogVisible = dialogState.collectAsState().value.isVisible,
                        delayTime = 3000,
                        title = { Text(text = stringResource(id = R.string.login_screen__text__reset_password_successful_title)) },
                        text = { Text(stringResource(id = R.string.login_screen__text__reset_password_successful_text)) },
                        confirmButton = { TextButton(onClick = {
                            onDialogDismissed()
                            hideDialog()
                        }) { Text(EMPTY_TEXT) } },
                    )
                }
                LoginDialogType.RESET_PASSWORD_ERROR -> {
                    ErrorDialog(
                        title = { Text(text = stringResource(id = R.string.login_screen__text__reset_password_error_title)) },
                        text = { Text(text = errorMessage ?: stringResource(id = R.string.login_screen__text__generic_error_text)) },
                        confirmButton = {
                            TextButton(onClick = { hideDialog() }) {
                                Text(
                                    stringResource(id = R.string.login_screen__text__ok)
                                )
                            }
                        },
                        onDismiss = {
                            onDialogDismissed()
                            hideDialog()
                        },
                        isDialogVisible = dialogState.collectAsState().value.isVisible
                    )
                }
                else -> {
                    ErrorDialog(
                        onDismiss = { hideDialog() },
                        text = { Text(text = stringResource(id = R.string.login_screen__text__generic_error_text)) },
                        confirmButton = {
                            TextButton(onClick = { hideDialog() }) {
                                Text(
                                    stringResource(id = R.string.login_screen__text__ok)
                                )
                            }
                        },
                    )
                }
            }
        }
    }
}

private fun clearFocus(focusManager: FocusManager, loginViewModel: LoginViewModel) {
    focusManager.clearFocus()
    loginViewModel.onUsernameFocusChanged(false)
    loginViewModel.onPasswordFocusChanged(false)
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoginPreview() {
    OpositateTheme {
        LoginScreen(rememberNavController())
    }
}

sealed class AnimationState {
    data object Idle : AnimationState()
    data object Loading : AnimationState()
    data object Finish : AnimationState()
    data object Dialog : AnimationState()
}