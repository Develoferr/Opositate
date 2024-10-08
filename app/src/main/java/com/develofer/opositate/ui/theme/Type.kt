package com.develofer.opositate.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.develofer.opositate.R

val Gotham = FontFamily(
    Font(R.font.gotham_thin, FontWeight.Thin),
    Font(R.font.gotham_thin_italic, FontWeight.Thin, FontStyle.Italic),
    Font(R.font.gotham_x_light, FontWeight.ExtraLight),
    Font(R.font.gotham_x_light_italic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.gotham_light, FontWeight.Light),
    Font(R.font.gotham_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.gotham_book, FontWeight.Normal),
    Font(R.font.gotham_book_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.gotham_medium, FontWeight.Medium),
    Font(R.font.gotham_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.gotham_bold, FontWeight.Bold),
    Font(R.font.gotham_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.gotham_black, FontWeight.Black),
    Font(R.font.gotham_black_italic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.gotham_ultra, FontWeight.ExtraBold),
    Font(R.font.gotham_ultra_italic, FontWeight.ExtraBold, FontStyle.Italic)
)

val AkzidenzGroteskBQ = FontFamily(
    // Light
    Font(R.font.akzidenz_grotesk_bq_light, FontWeight.Light),
    Font(R.font.akzidenz_grotesk_bq_light_it, FontWeight.Light, FontStyle.Italic),
    Font(R.font.akzidenz_grotesk_bq_light_os_f, FontWeight.Light),
    Font(R.font.akzidenz_grotesk_bq_light_sc, FontWeight.Light),
    Font(R.font.akzidenz_grotesk_bq_lig_ext, FontWeight.Light),
    Font(R.font.akzidenz_grotesk_bq_lig_ext_it, FontWeight.Light, FontStyle.Italic),
    Font(R.font.akzidenz_grotesk_bq_lig_cnd, FontWeight.Light),
    Font(R.font.akzidenz_grotesk_bq_lig_cnd_it, FontWeight.Light, FontStyle.Italic),

    // Regular
    Font(R.font.akzidenz_grotesk_bq_reg, FontWeight.Normal),
    Font(R.font.akzidenz_grotesk_bq_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.akzidenz_grotesk_bq_ext, FontWeight.Normal),
    Font(R.font.akzidenz_grotesk_bq_ext_it, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.akzidenz_grotesk_bq_cnd, FontWeight.Normal),
    Font(R.font.akzidenz_grotesk_bq_cnd_it, FontWeight.Normal, FontStyle.Italic),

    // Medium
    Font(R.font.akzidenz_grotesk_bq_medium, FontWeight.Medium),
    Font(R.font.akzidenz_grotesk_bq_med_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.akzidenz_grotesk_bq_med_ext, FontWeight.Medium),
    Font(R.font.akzidenz_grotesk_bq_med_ext_it, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.akzidenz_grotesk_bq_md_cnd, FontWeight.Medium),
    Font(R.font.akzidenz_grotesk_bq_md_cnd_it, FontWeight.Medium, FontStyle.Italic),

    // Bold
    Font(R.font.akzidenz_grotesk_bq_bold, FontWeight.Bold),
    Font(R.font.akzidenz_grotesk_bq_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.akzidenz_grotesk_bq_bold_ext, FontWeight.Bold),
    Font(R.font.akzidenz_grotesk_bq_bold_ext_it, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.akzidenz_grotesk_bq_bd_cnd, FontWeight.Bold),
    Font(R.font.akzidenz_grotesk_bq_bd_cnd_it, FontWeight.Bold, FontStyle.Italic),

    // Extra Bold
    Font(R.font.akzidenz_grotesk_bq_x_bold, FontWeight.ExtraBold),
    Font(R.font.akzidenz_grotesk_bq_x_bold_it, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.akzidenz_grotesk_bq_x_bold_alt, FontWeight.ExtraBold),
    Font(R.font.akzidenz_grotesk_bq_x_bd_cnd, FontWeight.ExtraBold),
    Font(R.font.akzidenz_grotesk_bq_x_bd_cnd_it, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.akzidenz_grotesk_bq_x_bd_cnd_alt, FontWeight.ExtraBold),
    Font(R.font.akzidenz_grotesk_bq_x_bd_cnd_it_alt, FontWeight.ExtraBold, FontStyle.Italic),

    // Super
    Font(R.font.akzidenz_grotesk_bq_super, FontWeight.Black),
    Font(R.font.akzidenz_grotesk_bq_super_italic, FontWeight.Black, FontStyle.Italic)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)