package com.develofer.opositate.presentation.register

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
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
import com.develofer.opositate.presentation.login.custom.CustomLoginLogoImage
import com.develofer.opositate.presentation.login.custom.CustomLoginTextButton
import com.develofer.opositate.presentation.login.custom.CustomLoginTextField
import com.develofer.opositate.presentation.login.custom.CustomSubtitleText
import com.develofer.opositate.presentation.login.custom.CustomTitleText
import com.develofer.opositate.presentation.custom.DialogState
import com.develofer.opositate.presentation.custom.ErrorDialog
import com.develofer.opositate.presentation.custom.SuccessDialog
import com.develofer.opositate.presentation.login.screen.AnimationState
import com.develofer.opositate.presentation.navigation.navigateToLogin
import com.develofer.opositate.ui.theme.OpositateTheme
import com.develofer.opositate.utils.Constants.EMPTY_TEXT
import kotlinx.coroutines.flow.StateFlow

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
        CustomLoginLogoImage(
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
        RegisterLoadingAnimation(
            registerState = uiState.registerState,
            animationState = animationState,
            registerViewModel = registerViewModel,
            modifier = Modifier.align(Alignment.BottomCenter),
            onAnimationStateChanged = { newAnimationState -> animationState = newAnimationState }
        )
        HandleRegisterDialog(
            hideDialog = {
                animationState = AnimationState.Idle
                registerViewModel.hideDialog()
            },
            animationState = animationState,
            uiState = uiState,
            dialogState = dialogState,
            onAnimationStateChanged = { newAnimationState -> animationState = newAnimationState },
            navigateToLogin = { navigateToLogin(navController) },
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
        RegisterButtons(isDarkTheme, registerViewModel, navigateToLogin, clearFocus)
    }
}

@Composable
fun RegisterHeader(isDarkTheme: Boolean) {
    CustomTitleText(
        text = stringResource(id = R.string.register_screen__title_text__register),
        isDarkTheme = isDarkTheme
    )
    CustomSubtitleText(
        text = stringResource(id = R.string.register_screen__text__journey_begins),
        isDarkTheme = isDarkTheme
    )
    Spacer(modifier = Modifier.height(20.dp))
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
fun RegisterButtons(isDarkTheme: Boolean, registerViewModel: RegisterViewModel, navigateToLogin: () -> Unit, clearFocus: () -> Unit) {
    CustomLoginButton(
        onClick = {
            registerViewModel.register()
            clearFocus()
        },
        text = stringResource(id = R.string.register_screen__text_btn__register),
        isDarkTheme = isDarkTheme
    )
    CustomLoginTextButton(
        onClick = { navigateToLogin() },
        text = stringResource(id = R.string.register_screen__text_btn__already_have_account),
        isDarkTheme = isDarkTheme
    )
}

@Composable
private fun RegisterLoadingAnimation(
    registerState: RegisterState, animationState: AnimationState, registerViewModel: RegisterViewModel,
    modifier: Modifier, onAnimationStateChanged: (animationsState: AnimationState) -> Unit
) {
    if (registerState == RegisterState.Loading || animationState == AnimationState.Loading) {
        onAnimationStateChanged(AnimationState.Loading)
        LottieRegisterAnimation(
            modifier = modifier.zIndex(2f),
            registerViewModel
        ) { onAnimationStateChanged(AnimationState.Finish) }
    }
}

@Composable
private fun LottieRegisterAnimation(
    modifier: Modifier = Modifier, registerViewModel: RegisterViewModel, onFinish: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_anim7))
    val progress by animateLottieCompositionAsState(composition = composition, iterations = 1)
    if (progress >= 1f && registerViewModel.areFieldsValid()) {
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
private fun HandleRegisterDialog(
    hideDialog: () -> Unit, animationState: AnimationState, uiState: RegisterUiState,
    dialogState: StateFlow<DialogState<RegisterDialogType>>,
    onAnimationStateChanged: (animationState: AnimationState) -> Unit, navigateToLogin: () -> Unit) {

    if (animationState == AnimationState.Finish || animationState == AnimationState.Dialog) {
        onAnimationStateChanged(AnimationState.Dialog)
        if (dialogState.collectAsState().value.isVisible) {
            when (dialogState.collectAsState().value.dialogType) {
                RegisterDialogType.REGISTER_SUCCESS -> {
                    SuccessDialog(
                        onDismiss = {
                            hideDialog()
                            navigateToLogin()
                        },
                        isDialogVisible = dialogState.collectAsState().value.isVisible, delayTime = 3000,
                        title = { Text(text = stringResource(id = R.string.register_screen__title_text__login_successful)) },
                        text = { Text(stringResource(id = R.string.register_screen__text__login_successful)) },
                        confirmButton = { TextButton(onClick = {}) { Text(EMPTY_TEXT) } },
                    )
                }
                RegisterDialogType.REGISTER_ERROR -> {
                    val error: String? = if (uiState.registerState is RegisterState.Failure) {
                        uiState.registerState.error
                    } else null
                    ErrorDialog(
                        title = { Text(text = stringResource(id = R.string.register_screen__title_text__login_error)) },
                        text = { Text(text = error ?: stringResource(id = R.string.register_screen__text__reset_password_successful)) },
                        confirmButton = {
                            TextButton(onClick = { hideDialog() }) { Text(
                                stringResource(id = R.string.login_screen__text_btn__ok)
                            ) }
                        },
                        onDismiss = { hideDialog() },
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

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun RegisterPreview() {
    OpositateTheme {
        RegisterScreen(rememberNavController())
    }
}