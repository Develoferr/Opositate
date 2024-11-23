package com.develofer.opositate.feature.profile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.develofer.opositate.ui.theme.Gray900
import com.develofer.opositate.ui.theme.Primary
import com.develofer.opositate.ui.theme.Secondary

@Composable
fun CustomDualProgressBar(
    modifier: Modifier = Modifier,
    primaryProgress: Float,
    secondaryProgress: Float
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(12.dp)
    ) {

        ProgressBar(
            progress = 1f,
            color = Gray900,
            modifier = Modifier
                .height(20.dp).clip(RoundedCornerShape(8.dp))
        )

        ProgressBar(
            progress = primaryProgress,
            color = Primary,
            modifier = Modifier.alpha(0.2f)
                .height(20.dp).clip(RoundedCornerShape(8.dp))
        )

        ProgressBar(
            progress = secondaryProgress,
            color = Secondary,
            modifier = Modifier.alpha(0.4f)
                .height(20.dp).clip(RoundedCornerShape(8.dp))
        )
    }
}

@Composable
fun ProgressBar(
    progress: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth(progress)
            .background(color)
    )
}

@Preview(showBackground = true)
@Composable
fun CustomDualProgressBarPreview() {
    CustomDualProgressBar(primaryProgress = 0.6f, secondaryProgress = 0.1f)
}
