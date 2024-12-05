package com.develofer.opositate.feature.login.presentation.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.develofer.opositate.main.components.common.LoadingButton
import com.develofer.opositate.main.data.model.UiResult
import com.develofer.opositate.ui.theme.ErrorLight
import com.develofer.opositate.ui.theme.Gray200
import com.develofer.opositate.ui.theme.Primary
import com.develofer.opositate.ui.theme.Secondary

@Composable
fun LoginLoadingButton(
    modifier: Modifier,
    loadingState: UiResult,
    onclick: () -> Unit,
    onAnimationComplete: () -> Unit,
    isDarkTheme: Boolean,
    text: String,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    LoadingButton(
        modifier = modifier,
        text = text,
        state = loadingState, onClick = {
            onclick()
        }, onAnimationComplete = {
            onAnimationComplete()
        },
        idleBackgroundColor = if (isDarkTheme) Primary else Color.Black,
        successBackgroundColor = if (isDarkTheme) Secondary else Secondary,
        errorBackgroundColor = if (isDarkTheme) ErrorLight else ErrorLight,
        loadingIndicatorColor = if (isDarkTheme) Color.Black else Gray200,
        errorIndicatorColor = if (isDarkTheme) Color.Black else Color.Black,
        successIndicatorColor = if (isDarkTheme) Color.Black else Color.Black,
        textComponent = {
            Text(
                text = text,
                fontSize = if (isDarkTheme) 20.sp else 25.sp,
                style = MaterialTheme.typography.titleMedium,
                color = if (isDarkTheme) Color.Black else Gray200,
            )
        },
        leadingIcon = leadingIcon,
        roundedCornerShape = 13.dp
    )
}