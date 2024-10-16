package com.develofer.opositate.presentation.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.graphics.ColorFilter
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
import com.develofer.opositate.presentation.viewmodel.TextFieldErrors.ValidateFieldErrors
import kotlinx.coroutines.delay

@Composable
fun CustomLoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isFocused: Boolean,
    onFocusChange: (Boolean) -> Unit,
    isPasswordField: Boolean = false,
    supportingText: ValidateFieldErrors,
    isDarkTheme: Boolean,
    textLetterSpacing: TextUnit = 2.sp,
    labelFontSize: TextUnit = 15.sp,
    textFieldPaddingBottom: Dp = 0.dp,
    haveToolTip: Boolean = false,
    toolTipText: String = "Test tooltip",
    painter: Painter = painterResource(id = R.drawable.ic_info_bold),
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    val fieldColors = getLoginFieldColors(isDarkTheme)
    var showTooltip by remember { mutableStateOf(false) }
    var tooltipPosition by remember { mutableStateOf(Offset.Zero) }
    var iconSize by remember { mutableStateOf(IntSize.Zero) }
    val trailingIcon: @Composable (() -> Unit)? =
        if (isPasswordField) {{
            val padding = if (isPasswordVisible) (-4).dp else 0.dp
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                val modifier =
                    if (isFocused || value.isNotEmpty()) Modifier.offset(y = 8.dp)
                    else Modifier.offset(y = 12.dp)
                Image(
                    painter = if (isPasswordVisible) painterResource(id = R.drawable.ic_open_eye)
                    else painterResource(id = R.drawable.ic_closed_eye),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                    contentDescription = stringResource(id = R.string.custom_login_text_field_password_eye_image_content_description),
                    modifier = modifier
                        .size(24.dp)
                        .offset(y = padding)
                )
            }
        }} else if (haveToolTip) {{
            Icon(
                painter = painter,
                contentDescription = "Info Icon",
                modifier = Modifier
                    .offset(y = 8.dp).size(16.dp)
                    .onGloballyPositioned { coordinates: LayoutCoordinates ->
                        iconSize = coordinates.size
                        tooltipPosition = coordinates.localToWindow(Offset.Zero)
                    }
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                showTooltip = true
                                delay(4500)
                                showTooltip = false
                            }
                        )
                    }
            )
        }} else null

    TextField(
        singleLine = true,
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                maxLines = 1,
                text = label,
                fontSize =
                    if (isFocused || value.isNotEmpty()) 10.sp
                    else labelFontSize,
                letterSpacing = 2.sp,
                color = MaterialTheme.colorScheme.onBackground,
                style =
                    if (isFocused || value.isNotEmpty()) MaterialTheme.typography.labelMedium
                    else MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Light,
                modifier =
                    if (isFocused || value.isNotEmpty()) Modifier.offset(x = (-17).dp)
                    else Modifier.offset(y = 13.dp, x = (-17).dp)
            )
        }, supportingText = {
            val supportingTextFieldError = when (supportingText) {
                ValidateFieldErrors.INVALID_EMAIL -> stringResource(
                    id = R.string.login_screen__supporting_text__invalid_email
                )
                ValidateFieldErrors.EMPTY_TEXT -> stringResource(
                    id = R.string.login_screen__supporting_text__empty_field
                )
                ValidateFieldErrors.EMAILS_DO_NOT_MATCH -> stringResource(
                    id = R.string.resetPassword_dialog__supporting_text__emails_do_not_match
                )
                else -> ""
            }
            Text(
                text = supportingTextFieldError,
                fontSize = if (isSystemInDarkTheme()) 10.sp else 10.sp,
                letterSpacing = textLetterSpacing,
                color = MaterialTheme.colorScheme.error
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = fieldColors.containerColor,
            unfocusedContainerColor = fieldColors.containerColor,
            disabledContainerColor = fieldColors.containerColor,
            focusedIndicatorColor = fieldColors.indicatorColor,
            unfocusedIndicatorColor = fieldColors.indicatorColor,
            disabledIndicatorColor = fieldColors.indicatorColor,
            cursorColor = fieldColors.cursorColor,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledTextColor = Color.Black,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 45.dp)
            .padding(textFieldPaddingBottom)
            .height(76.dp)
            .onFocusChanged { onFocusChange(it.isFocused) },
        textStyle =
            TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Normal,
                fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                fontSize = 16.sp,
                letterSpacing = 1.sp
            ),
        visualTransformation =
            if (isPasswordField && !isPasswordVisible) PasswordVisualTransformation()
            else VisualTransformation.None,
        trailingIcon = trailingIcon
        ,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = { onValueChange("") })
    )
    if (showTooltip) {
        Tooltip(tooltipPosition, iconSize, toolTipText = toolTipText)
    }
}


@Composable
fun Tooltip(position: Offset, iconSize: IntSize, toolTipText: String) {
    val density = LocalDensity.current
    Popup(
        alignment = Alignment.TopStart,
        offset = IntOffset((position.x.toInt()-150), (position.y.toInt() + 50))
    ) {
        Box(
            modifier = Modifier
                .background(Color.Gray)
                .wrapContentSize()
        ) {
            Text(
                text = toolTipText,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


@Composable
private fun getLoginFieldColors(isDarkTheme: Boolean): LoginFieldColors {
    val containerColor = if (isDarkTheme) Color.Black else MaterialTheme.colorScheme.primary
    val indicatorColor = if (isDarkTheme) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
    val cursorColor = if (isDarkTheme) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
    return LoginFieldColors(containerColor, indicatorColor, cursorColor)
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
        value = "",
        onValueChange = {},
        label = "USUARIO",
        isFocused = false,
        onFocusChange = {},
        isPasswordField = true,
        supportingText = ValidateFieldErrors.EMPTY_TEXT,
        isDarkTheme = true
    )
}