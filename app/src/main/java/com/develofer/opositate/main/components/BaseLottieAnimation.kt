package com.develofer.opositate.main.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun BaseLottieAnimation(
    modifier: Modifier = Modifier,
    surfaceColor: Color? = null,
    animRes: LottieCompositionSpec,
    onFinish: () -> Unit
) {
    val composition by rememberLottieComposition(animRes)
    val progress by animateLottieCompositionAsState(composition = composition, iterations = 1)
    if (progress >= 1f) LaunchedEffect(Unit) { onFinish() }
    surfaceColor?.let {
        Surface(modifier = Modifier.fillMaxSize().zIndex(2f), color = surfaceColor) {
            LottieAnimation(
                composition = composition, progress = { progress },
                modifier = modifier.zIndex(2f).offset(y = (50).dp)
            )
        }
    } ?: LottieAnimation(
        composition = composition, progress = { progress },
        modifier = modifier.zIndex(2f)
    )
}
