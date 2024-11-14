package com.develofer.opositate.feature.login.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.develofer.opositate.R
import com.develofer.opositate.feature.login.presentation.model.TextFieldErrors.ValidateFieldErrors
import com.develofer.opositate.utils.StringConstants.EMPTY_STRING
import kotlinx.coroutines.delay

@Composable
fun CustomLoginTextField(
    value: String, onValueChange: (String) -> Unit, label: String, isFocused: Boolean,
    onFocusChange: (Boolean) -> Unit, isPasswordField: Boolean = false,
    supportingText: ValidateFieldErrors, isDarkTheme: Boolean, textLetterSpacing: TextUnit = 2.sp,
    labelFontSize: TextUnit = 15.sp, textFieldPaddingBottom: Dp = 0.dp, haveToolTip: Boolean = false,
    toolTipText: String = EMPTY_STRING, painter: Painter = painterResource(id = R.drawable.ic_info)
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    var showTooltip by remember { mutableStateOf(false) }
    var tooltipPosition by remember { mutableStateOf(Offset.Zero) }
    var iconSize by remember { mutableStateOf(IntSize.Zero) }

    val fieldColors = getLoginFieldColors(isDarkTheme)
    val trailingIcon = getTrailingIcon(
        isPasswordField, haveToolTip, isPasswordVisible, isFocused, value, painter,
        { isPasswordVisible = !isPasswordVisible },
        { coordinates -> iconSize = coordinates.size; tooltipPosition = coordinates.localToWindow(Offset.Zero) },
        { newVisibility ->  showTooltip = newVisibility }
    )
    TextField(
        singleLine = true,
        value = value,
        onValueChange = onValueChange,
        label = getLabel(label, isFocused, value, labelFontSize),
        supportingText = getSupportingText(supportingText, textLetterSpacing),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = fieldColors.containerColor,
            unfocusedContainerColor = fieldColors.containerColor,
            focusedIndicatorColor = fieldColors.indicatorColor,
            unfocusedIndicatorColor = fieldColors.indicatorColor,
            cursorColor = fieldColors.cursorColor,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 45.dp)
            .padding(textFieldPaddingBottom)
            .height(76.dp)
            .onFocusChanged { onFocusChange(it.isFocused) },
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            letterSpacing = 1.sp
        ),
        visualTransformation = if (isPasswordField && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = { onValueChange(EMPTY_STRING) })
    )
    if (showTooltip) {
        Tooltip(tooltipPosition, iconSize, toolTipText)
    }
}

@Composable
private fun getLabel(
    label: String, isFocused: Boolean, value: String, labelFontSize: TextUnit): @Composable () -> Unit
{
    return {
        Text(
            text = label,
            fontSize = if (isFocused || value.isNotEmpty()) 10.sp else labelFontSize,
            letterSpacing = 2.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Light,
            modifier = if (isFocused || value.isNotEmpty()) Modifier.offset(x = (-17).dp) else Modifier.offset(y = 13.dp, x = (-17).dp)
        )
    }
}

@Composable
private fun getSupportingText(
    supportingText: ValidateFieldErrors, letterSpacing: TextUnit
): @Composable () -> Unit {
    val supportingTextFieldError = when (supportingText) {
        ValidateFieldErrors.INVALID_EMAIL -> stringResource(id = R.string.custom_login_text_field__supporting_text__invalid_email)
        ValidateFieldErrors.EMPTY_TEXT -> stringResource(id = R.string.custom_login_text_field__supporting_text__empty_field)
        ValidateFieldErrors.EMAILS_DO_NOT_MATCH -> stringResource(id = R.string.custom_login_text_field__supporting_text__emails_do_not_match)
        else -> EMPTY_STRING
    }
    return {
        Text(
            text = supportingTextFieldError,
            fontSize = 10.sp,
            letterSpacing = letterSpacing,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun getLoginFieldColors(isDarkTheme: Boolean): LoginFieldColors {
    val containerColor = Transparent
    val indicatorColor = if (isDarkTheme) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
    val cursorColor = if (isDarkTheme) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
    return LoginFieldColors(containerColor, indicatorColor, cursorColor)
}

@Composable
private fun getTrailingIcon(
    isPasswordField: Boolean, haveToolTip: Boolean, isPasswordVisible: Boolean, isFocused: Boolean,
    value: String, painter: Painter, onPasswordToggle: () -> Unit, onGloballyPositioned: (LayoutCoordinates) -> Unit,
    toggleToolTipVisibility: (newVisibility: Boolean) -> Unit
): @Composable (() -> Unit)? {
    return when {
        isPasswordField -> {
            val padding = if (isPasswordVisible) (9).dp else (13).dp
            {
                IconButton(onClick = onPasswordToggle) {
                    Icon(
                        painter = if (isPasswordVisible) painterResource(id = R.drawable.ic_open_eye) else painterResource(id = R.drawable.ic_closed_eye_png),
                        contentDescription = stringResource(id = R.string.custom_login_text_field__content_description__password_eye_image),
                        modifier = Modifier.size(24.dp).offset(y = padding)
                    )
                }
            }
        }
        haveToolTip -> {
            {
                Icon(
                    painter = painter,
                    contentDescription = stringResource(id = R.string.custom_login_text_field__content_description__ic_info),
                    modifier = Modifier
                        .offset(y = 8.dp).size(24.dp)
                        .onGloballyPositioned(onGloballyPositioned)
                        .pointerInput(Unit) { detectTapGestures(onPress = {
                            toggleToolTipVisibility(true)
                            delay(4500)
                            toggleToolTipVisibility(false)
                        }) }
                )
            }
        }
        else -> null
    }
}

@Composable
fun Tooltip(
    position: Offset, iconSize: IntSize, toolTipText: String
) {
    val density = LocalDensity.current
    Popup(
        alignment = Alignment.TopStart,
        offset = IntOffset((position.x.toInt()-150), (position.y.toInt() + 50))
    ) {
        Box(
            modifier = Modifier.background(Color.Gray).wrapContentSize()
        ) {
            Text(
                text = toolTipText,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

private data class LoginFieldColors(
    val containerColor: Color,
    val indicatorColor: Color,
    val cursorColor: Color
)

@Preview
@Composable
fun CustomLoginTextFieldPreview() {
    CustomLoginTextField(
        value = EMPTY_STRING,
        onValueChange = {},
        label = "USER",
        isFocused = false,
        onFocusChange = {},
        isPasswordField = true,
        supportingText = ValidateFieldErrors.EMPTY_TEXT,
        isDarkTheme = true
    )
}