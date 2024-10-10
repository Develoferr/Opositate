package com.develofer.opositate.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.core.view.WindowCompat

val LightColorScheme = lightColorScheme(
    primary = Primary400,
    primaryContainer = Primary600,
    secondary = Secondary400,
    secondaryContainer = Secondary600,
    background = Primary,
    surface = Gray100,
    error = Color(0xFFB00020),
    onPrimary = Gray900,
    onSecondary = Color.White,
    onBackground = TextPrimaryLight,
    onSurface = TextSecondaryLight,
    onError = Color.White
)

val DarkColorScheme = darkColorScheme(
    primary = Primary300,
    primaryContainer = Primary700,
    secondary = Secondary300,
    secondaryContainer = Secondary700,
//    background = Gray900,
//    background = Color(0xFF121212),
    background = Color.Black,
//    background = Color(0xFF303030),
    surface = Color(0xFF1E1E1E),
    error = Color(0xFFCF6679),
    onPrimary = TextPrimaryDark,
    onSecondary = TextPrimaryDark,
    onBackground = TextPrimaryDark,
    onSurface = TextSecondaryDark,
    onError = Color.Black
)

fun applyFontFamily(fontFamily: FontFamily): Typography {
    return Typography(
        displayLarge = Typography.displayLarge.copy(fontFamily = fontFamily),
        displayMedium = Typography.displayMedium.copy(fontFamily = fontFamily),
        displaySmall = Typography.displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = Typography.headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = Typography.headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = Typography.headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = Typography.titleLarge.copy(fontFamily = fontFamily),
        titleMedium = Typography.titleMedium.copy(fontFamily = fontFamily),
        titleSmall = Typography.titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = Typography.bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = Typography.bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = Typography.bodySmall.copy(fontFamily = fontFamily),
        labelLarge = Typography.labelLarge.copy(fontFamily = fontFamily),
        labelMedium = Typography.labelMedium.copy(fontFamily = fontFamily),
        labelSmall = Typography.labelSmall.copy(fontFamily = fontFamily),
    )
}

@Composable
fun appTypography(): Typography {
    return if (isSystemInDarkTheme()) {
        applyFontFamily(AkzidenzGroteskBQ)
    } else {
        applyFontFamily(Gotham)
    }
}

@Composable
fun OpositateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = appTypography(),
        content = content
    )
}