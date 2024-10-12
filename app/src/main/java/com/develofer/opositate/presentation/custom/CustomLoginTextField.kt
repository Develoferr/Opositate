package com.develofer.opositate.presentation.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.develofer.opositate.R

@Composable
fun CustomLoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isFocused: Boolean,
    onFocusChange: (Boolean) -> Unit,
    isPasswordField: Boolean = false,
    containerColor: Color,
    indicatorColor: Color,
    cursorColor: Color,
    supportingText: String
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                fontSize =
                    if (isFocused || value.isNotEmpty()) 10.sp
                    else 15.sp,
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
            if (supportingText.isNotEmpty()) {
                Text(
                    text = supportingText,
                    fontSize = if (isSystemInDarkTheme()) 9.sp else 10.sp,
                    letterSpacing = 2.sp,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            focusedIndicatorColor = indicatorColor,
            unfocusedIndicatorColor = indicatorColor,
            disabledIndicatorColor = indicatorColor,
            cursorColor = cursorColor,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledTextColor = Color.Black,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 45.dp)
            .padding(top = 10.dp)
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
        trailingIcon =
        if (isPasswordField) {
            {
                IconButton(onClick = {isPasswordVisible = !isPasswordVisible}) {
                    val modifier =
                        if (isFocused || value.isNotEmpty()) Modifier.offset(y = 8.dp)
                        else Modifier.offset(y = 12.dp)
                    Image(

                        painter =
                            if (isPasswordVisible) painterResource(id = R.drawable.ic_open_eye)
                            else painterResource(id = R.drawable.ic_closed_eye),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                        contentDescription = stringResource(id = R.string.custom_login_text_field_password_eye_image_content_description),
                        modifier = modifier.size(24.dp)
                    )
                }
            }
        }
        else null
    )
}