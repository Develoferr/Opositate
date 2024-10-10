package com.develofer.opositate.ui

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.develofer.opositate.R
import com.develofer.opositate.ui.custom.CustomLoginTextField
import com.develofer.opositate.ui.theme.Gray200
import com.develofer.opositate.ui.theme.OpositateTheme
import java.util.Locale

@Composable
fun LoginScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isUsernameFocused by remember { mutableStateOf(false) }
    var isPasswordFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

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
            contentDescription = "imagen cerebro",
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

            val displayText = if (isSystemInDarkTheme()) "Bienvenido".uppercase() else "Bienvenido"
            Text(
                text = displayText,
                fontSize = if (isSystemInDarkTheme()) 36.sp else 50.sp,
                color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(top = (24).dp)
            )
            val text = "Inicia sesión para continuar"
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
                    onValueChange = { username = it },
                    label = "USUARIO",
                    isFocused = isUsernameFocused,
                    onFocusChange = { isUsernameFocused = it },
                    isPasswordField = false,
                    containerColor = containerColor,
                    indicatorColor = indicatorColor,
                    cursorColor = cursorColor
                )

                CustomLoginTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "CONTRASEÑA",
                    isFocused = isPasswordFocused,
                    onFocusChange = { isPasswordFocused = it },
                    isPasswordField = true,
                    containerColor = containerColor,
                    indicatorColor = indicatorColor,
                    cursorColor = cursorColor
                )

                TextButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(bottom = 5.dp, start = 50.dp)
                        .align(Alignment.Start),
                ) {
                    Text(
                        text = "¿HAS OLVIDADO TU CONTRASEÑA?",
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
                    onClick = {},
                    shape = RoundedCornerShape(13.dp),
                    modifier = Modifier.padding(top = 20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundColor)
                ) {

                    Text(
                        text = "GO",
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
                        text = "LOGIN CON GOOGLE",
                        fontSize = if (isSystemInDarkTheme()) 20.sp else 25.sp,
                        style = MaterialTheme.typography.titleMedium,
                        color = Gray200,
                    )
                }

                TextButton(
                    onClick = {},
                    modifier = Modifier.padding(vertical = 5.dp)
                ) {
                    Text(
                        text = "¿NUEVO USUARIO?\nREGÍSTRATE",
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

//@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
//@Composable
//fun LoginPreview() {
//    OpositateTheme {
//        LoginScreen()
//    }
//}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    OpositateTheme {
        LoginScreen()
    }
}