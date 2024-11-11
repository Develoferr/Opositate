package com.develofer.opositate.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.core.view.WindowCompat

val LightColorScheme = lightColorScheme(
    // Surface Colors
    background = Primary,
    onBackground = TextPrimaryLight,
    surface = Gray100,
    onSurface = TextSecondaryLight,
    primaryContainer = Gray400,
    onPrimaryContainer = TextPrimaryLight,
    secondaryContainer = Gray600,
    onSecondaryContainer = Color.Black,
    outline = Gray600,
    outlineVariant = Gray800,
    // Accent Colors
    primary = Primary400,
    onPrimary = Gray900,
    secondary = Secondary400,
    onSecondary = Color.White,
    error = ErrorLight,
    onError = Color.White,
)

val DarkColorScheme = darkColorScheme(
    // Surface Colors
    background = Color.Black,
    onBackground = TextPrimaryDark,
    surface = Color(0xFF1E1E1E),
    onSurface = TextSecondaryDark,
    primaryContainer = Gray800,
    onPrimaryContainer = TextPrimaryDark,
    secondaryContainer = Gray600,
    onSecondaryContainer = Color.White,
    outline = Gray400,
    outlineVariant = Gray200,
    // Accent Colors
    primary = Primary300,
    onPrimary = TextPrimaryDark,
    secondary = Secondary300,
    onSecondary = TextPrimaryDark,
    error = ErrorDark,
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