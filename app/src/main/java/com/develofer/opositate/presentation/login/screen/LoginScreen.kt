package com.develofer.opositate.presentation.login.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.develofer.opositate.R
import com.develofer.opositate.presentation.login.custom.CustomLoginButton
import com.develofer.opositate.presentation.login.custom.CustomLoginTextButton
import com.develofer.opositate.presentation.login.custom.CustomLoginTextField
import com.develofer.opositate.presentation.login.custom.CustomSubtitleText
import com.develofer.opositate.presentation.login.custom.CustomTitleText
import com.develofer.opositate.ui.custom.DialogState
import com.develofer.opositate.ui.custom.ErrorDialog
import com.develofer.opositate.ui.custom.SuccessDialog
import com.develofer.opositate.presentation.login.custom.CustomLoginLogo
import com.develofer.opositate.presentation.login.model.LoginDialogType
import com.develofer.opositate.presentation.login.model.LoginState
import com.develofer.opositate.presentation.login.model.LoginUiState
import com.develofer.opositate.presentation.login.resetpassword.ResetPasswordDialog
import com.develofer.opositate.ui.navigation.AppRoutes.Destination
import com.develofer.opositate.ui.navigation.navigateToHome
import com.develofer.opositate.presentation.login.viewmodel.LoginViewModel
import com.develofer.opositate.ui.main.MainViewModel
import com.develofer.opositate.ui.theme.OpositateTheme
import kotlinx.coroutines.flow.StateFlow

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
    var registerErrorMessage: String? by remember { mutableStateOf(null) }

    mainViewModel.hideSystemUI()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
    ) {
        CustomLoginLogo(
            isDarkTheme = isDarkTheme, isKeyboardVisible = isKeyboardVisible,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        LoginContent(
            uiState = uiState, loginViewModel = loginViewModel, isDarkTheme = isDarkTheme,
            isKeyBoardVisible = isKeyboardVisible, clearFocus = { focusManager.clearFocus() },
            onForgotPasswordClick = { loginViewModel.toggleResetPasswordDialogVisibility(true) },
            navigateToRegister = { navController.navigate(Destination.REGISTER.route) }
        )
        LoginResetPasswordDialog(
            showResetPasswordDialog = uiState.showResetPasswordDialog, focusManager = focusManager,
            loginViewModel = loginViewModel, onPasswordFinished = { passwordResetFinished = true},
            hideDialog = { loginViewModel.toggleResetPasswordDialogVisibility(false) },
            saveErrorMessage = { newErrorMessage -> registerErrorMessage = newErrorMessage },
        )
        LoginLoadingAnimation(
            loginState = uiState.loginState, animationState = animationState,
            loginViewModel = loginViewModel, modifier = Modifier.align(Alignment.BottomCenter),
            onAnimationStateChanged = { newAnimationState -> animationState = newAnimationState }
        )
        HandleDialog(
            hideDialog = {
                animationState = AnimationState.Idle
                loginViewModel.hideDialog()
            },
            animationState = animationState, uiState = uiState, errorMessage = registerErrorMessage,
            dialogState = dialogState, passwordResetFinished = passwordResetFinished,
            onAnimationStateChanged = { newAnimationState -> animationState = newAnimationState },
            navigateToHome = { navigateToHome(navController) }, onDialogDismissed = { passwordResetFinished = false }
        )
    }
}

@Composable
private fun LoginContent(
    uiState: LoginUiState, loginViewModel: LoginViewModel, isDarkTheme: Boolean, isKeyBoardVisible: Boolean,
    onForgotPasswordClick: () -> Unit, clearFocus: () -> Unit, navigateToRegister: () -> Unit,
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
        LoginButtons(loginViewModel, isDarkTheme, clearFocus, navigateToRegister)
    }
}

@Composable
private fun LoginHeader(isDarkTheme: Boolean) {
    val text = stringResource(id = R.string.login_screen__title_text__welcome)
    CustomTitleText(
        text = if (isDarkTheme) text.uppercase() else text,
        isDarkTheme = isDarkTheme
    )
    CustomSubtitleText(
        text = stringResource(id = R.string.login_screen__text__sign_in),
        isDarkTheme = isDarkTheme
    )
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
private fun LoginFields(
    uiState: LoginUiState, loginViewModel: LoginViewModel, modifier: Modifier,
    isDarkTheme: Boolean, onForgotPasswordClick: () -> Unit
) {
    CustomLoginTextField(
        value = uiState.username,
        onValueChange = {
            loginViewModel.onUsernameChanged(it)
            loginViewModel.validateUsername()
        },
        label = stringResource(id = R.string.login_screen__label_text_field__user).uppercase(), isFocused = uiState.isUsernameFocused,
        onFocusChange = { loginViewModel.onUsernameFocusChanged(it) }, isPasswordField = false,
        supportingText = uiState.usernameValidateFieldError, isDarkTheme = isDarkTheme
    )
    CustomLoginTextField(
        value = uiState.password,
        onValueChange = {
            loginViewModel.onPasswordChanged(it)
            loginViewModel.validatePassword()
        },
        label = stringResource(id = R.string.login_screen__label_text_field__password).uppercase(), isFocused = uiState.isPasswordFocused,
        onFocusChange = { loginViewModel.onPasswordFocusChanged(it) }, isPasswordField = true,
        supportingText = uiState.passwordValidateFieldError, isDarkTheme = isDarkTheme
    )
    ForgotPasswordButton(modifier, isDarkTheme, onForgotPasswordClick)
}

@Composable
private fun ForgotPasswordButton(modifier: Modifier, isDarkTheme: Boolean, onForgotPasswordClick: () -> Unit) {
    CustomLoginTextButton(
        onClick = { onForgotPasswordClick() }, isDarkTheme = isDarkTheme, modifier = modifier.offset(y = (-14).dp),
        text = stringResource(id = R.string.login_screen__text__forget_your_password),
    )
}

@Composable
private fun LoginButtons(
    loginViewModel: LoginViewModel, isDarkTheme: Boolean,
    clearFocus: () -> Unit = {}, navigateToRegister: () -> Unit
) {
    Spacer(modifier = Modifier.height(16.dp))
    LoginButton(
        onClick = {
            loginViewModel.login()
            clearFocus()
        },
        isDarkTheme = isDarkTheme
    )
    Spacer(modifier = Modifier.height(16.dp))
    GoogleLoginButton(
        onClick = {

        }, isDarkTheme = isDarkTheme)
    Spacer(modifier = Modifier.height(12.dp))
    GoToRegisterButton(onClick = { navigateToRegister() }, isDarkTheme)
}

@Composable
private fun LoginButton(onClick: () -> Unit, isDarkTheme: Boolean) {
    CustomLoginButton(
        text = stringResource(id = R.string.login_screen__text_btn__go).uppercase(),
        onClick = onClick,
        isDarkTheme = isDarkTheme
    )
}

@Composable
private fun GoogleLoginButton(onClick: () -> Unit, isDarkTheme: Boolean) {
    CustomLoginButton(
        text = stringResource(id = R.string.login_screen__text_btn__google),
        onClick = { onClick() },
        isDarkTheme = isDarkTheme,
        icon = painterResource(id = R.drawable.ic_google_icon),
    )
}

@Composable
private fun GoToRegisterButton(onClick: () -> Unit, isDarkTheme: Boolean) {
    CustomLoginTextButton(
        onClick = { onClick() }, isDarkTheme = isDarkTheme,
        text = stringResource(id = R.string.login_screen__text_btn__new_user).uppercase()
    )
}

@Composable
fun LoginResetPasswordDialog(
    showResetPasswordDialog: Boolean, focusManager: FocusManager, loginViewModel: LoginViewModel,
    hideDialog: () -> Unit, saveErrorMessage: (String) -> Unit, onPasswordFinished: () -> Unit
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
private fun LoginLoadingAnimation(
    loginState: LoginState, animationState: AnimationState, loginViewModel: LoginViewModel,
    modifier: Modifier, onAnimationStateChanged: (animationsState: AnimationState) -> Unit
) {
    if (loginState == LoginState.Loading || animationState == AnimationState.Loading) {
        onAnimationStateChanged(AnimationState.Loading)
        LottieLoadingAnimation(
            modifier = modifier.zIndex(2f),
            loginViewModel
        ) { onAnimationStateChanged(AnimationState.Finish) }
    }
}

@Composable
private fun LottieLoadingAnimation(
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
        modifier = modifier
            .fillMaxSize()
            .alpha(1f),
        color = MaterialTheme.colorScheme.background
    ) {
        LottieAnimation(
            composition = composition, progress = { progress },
            modifier = modifier
                .size(200.dp)
                .alpha(1f)
                .offset(y = (50).dp)
        )
    }
}

@Composable
private fun HandleDialog(
    animationState: AnimationState, uiState: LoginUiState, errorMessage: String?,
    onAnimationStateChanged: (animationState: AnimationState) -> Unit,
    navigateToHome: () -> Unit, dialogState: StateFlow<DialogState<LoginDialogType>>,
    hideDialog: () -> Unit, passwordResetFinished: Boolean, onDialogDismissed: () -> Unit
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
                            navigateToHome()
                        },
                        isDialogVisible = dialogState.collectAsState().value.isVisible,
                        delayTime = 3000,
                        title = { Text(text = stringResource(id = R.string.login_screen__title_text__login_successful)) },
                        text = { Text(stringResource(id = R.string.login_screen__text__login_successful)) },
                    )
                }
                LoginDialogType.LOGIN_ERROR -> {
                    val error: String? = if (uiState.loginState is LoginState.Failure) {
                        uiState.loginState.error
                    } else null
                    ErrorDialog(
                        title = { Text(text = stringResource(id = R.string.login_screen__title_text__login_error)) },
                        text = { Text(text = error ?: stringResource(id = R.string.login_screen__text__generic_error)) },
                        confirmButton = {
                            TextButton(onClick = { hideDialog() }) { Text(
                                stringResource(id = R.string.login_screen__text_btn__ok)
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
                        title = { Text(text = stringResource(id = R.string.login_screen__title_text__reset_password_successful)) },
                        text = { Text(stringResource(id = R.string.login_screen__text__reset_password_successful)) },
                    )
                }
                LoginDialogType.RESET_PASSWORD_ERROR -> {
                    ErrorDialog(
                        title = { Text(text = stringResource(id = R.string.login_screen__text__reset_password_error)) },
                        text = { Text(text = errorMessage ?: stringResource(id = R.string.login_screen__text__generic_error)) },
                        confirmButton = {
                            TextButton(onClick = { hideDialog() }) {
                                Text(
                                    stringResource(id = R.string.login_screen__text_btn__ok)
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
                        text = { Text(text = stringResource(id = R.string.login_screen__text__generic_error)) },
                        confirmButton = {
                            TextButton(onClick = { hideDialog() }) {
                                Text(
                                    stringResource(id = R.string.login_screen__text_btn__ok)
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

sealed class AnimationState {
    data object Idle : AnimationState()
    data object Loading : AnimationState()
    data object Finish : AnimationState()
    data object Dialog : AnimationState()
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoginPreview() {
    OpositateTheme {
        LoginScreen(rememberNavController())
    }
}
