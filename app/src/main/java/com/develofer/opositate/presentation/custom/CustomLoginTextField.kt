package com.develofer.opositate.presentation.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
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
    cursorColor: Color
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                fontSize = if (isFocused) 10.sp
                else (
                        if (value.isEmpty()) 15.sp
                        else 10.sp
                        ),
                letterSpacing = 2.sp,
                color = MaterialTheme.colorScheme.onBackground,
                style =
                if (isFocused) MaterialTheme.typography.labelMedium
                else (
                        if (value.isEmpty()) MaterialTheme.typography.bodyLarge
                        else MaterialTheme.typography.labelMedium
                        ),
                fontWeight = FontWeight.Light,
                modifier =
                if (isFocused) Modifier.offset(x = (-17).dp)
                else (
                        if (value.isEmpty()) Modifier.offset(y = 13.dp, x = (-17).dp)
                        else Modifier.offset(x = (-17).dp)
                        )
            )
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
        visualTransformation = if (isPasswordField) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = if (isPasswordField) {
            {
                IconButton(onClick = {}) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_eye),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                        contentDescription = "Toggle password visibility",
                        modifier = Modifier.offset(y = 12.dp)
                    )
                }
            }
        } else null
    )
}
