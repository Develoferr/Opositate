package com.develofer.opositate.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.develofer.opositate.ui.theme.Primary
import com.develofer.opositate.ui.theme.Secondary

@Composable
fun CustomDualProgressBar(
    primaryProgress: Float,
    secondaryProgress: Float
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .padding(vertical = 6.dp)
    ) {

        ProgressBar(
            progress = primaryProgress,
            color = Primary,
            modifier = Modifier
                .height(20.dp)
        )

        ProgressBar(
            progress = secondaryProgress,
            color = Secondary,
            modifier = Modifier
                .height(20.dp)
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
