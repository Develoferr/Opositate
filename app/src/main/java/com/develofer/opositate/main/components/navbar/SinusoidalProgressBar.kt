package com.develofer.opositate.main.components.navbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SinusoidalProgressBar(
    isVisible: Boolean,
    progress: Float,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            animationSpec = tween(durationMillis = 200)
        ),
        exit = fadeOut(
            animationSpec = tween(durationMillis = 200)
        )
    ) {
        LinearProgressIndicator(
            progress = { progress },
            modifier = modifier.fillMaxWidth().height(4.dp).padding(bottom = 2.dp),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
            trackColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
        )
    }
}


