package com.develofer.opositate.ui.screen

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.develofer.opositate.R
import com.develofer.opositate.ui.custom.CustomLoginTextField
import com.develofer.opositate.ui.theme.Gray200
import com.develofer.opositate.ui.theme.OpositateTheme
import java.util.Locale

@Composable
fun RegisterScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isUsernameFocused by remember { mutableStateOf(false) }
    var isEmailFocused by remember { mutableStateOf(false) }
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

            val displayText = if (isSystemInDarkTheme()) "Regístrate".uppercase() else "Regístrate"
            Text(
                text = displayText,
                fontSize = if (isSystemInDarkTheme()) 36.sp else 50.sp,
                color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(top = (24).dp)
            )
            val text = "para empezar tu viaje"
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
                    onValueChange = { username = it },
                    label = "Usuario".uppercase(),
                    isFocused = isUsernameFocused,
                    onFocusChange = { isUsernameFocused = it },
                    isPasswordField = false,
                    containerColor = containerColor,
                    indicatorColor = indicatorColor,
                    cursorColor = cursorColor
                )

                CustomLoginTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Correo".uppercase(),
                    isFocused = isEmailFocused,
                    onFocusChange = { isEmailFocused = it },
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

                val buttonBackgroundColor =
                    if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary
                    else Color.Black

                Button(
                    onClick = {},
                    shape = RoundedCornerShape(13.dp),
                    modifier = Modifier.padding(top = 50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundColor)
                ) {

                    Text(
                        text = "Registrarse".uppercase(),
                        fontSize = if (isSystemInDarkTheme()) 20.sp else 25.sp,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (isSystemInDarkTheme()) Color.Black else Gray200,
                    )
                }

                TextButton(
                    onClick = { navController.navigate("login") },
                    modifier = Modifier.padding(vertical = 5.dp)
                ) {
                    Text(
                        text = "¿Ya tienes una cuenta?\nEntra aquí".uppercase(),
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

//@Preview(showBackground = true)
//@Composable
//fun RegisterPreview() {
//    OpositateTheme {
//        RegisterScreen(rememberNavController())
//    }
//}