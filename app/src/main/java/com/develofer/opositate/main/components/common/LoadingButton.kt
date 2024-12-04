package com.develofer.opositate.main.components.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.develofer.opositate.R
import com.develofer.opositate.main.data.model.UiResult
import com.develofer.opositate.ui.theme.ErrorLight
import com.develofer.opositate.ui.theme.Primary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoadingButton(
    text: String,
    state: UiResult,
    onClick: () -> Unit,
    onAnimationComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = when (state) {
            is UiResult.Success -> Primary
            is UiResult.Error -> ErrorLight
            else -> Color.Black
        },
        animationSpec = tween(durationMillis = 500),
        label = "Background Color"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -360f,
        label = "Rotation",
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 600, easing = LinearEasing)
        )
    )

    val checkProgress = remember { Animatable(0f) }
    val crossProgress = remember { Animatable(0f) }

    LaunchedEffect(state) {
        when (state) {
            is UiResult.Loading -> {
                checkProgress.snapTo(0f)
                crossProgress.snapTo(0f)
            }
            is UiResult.Success -> {
                checkProgress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(1000, easing = LinearOutSlowInEasing)
                )
                launch {
                    delay(500)
                    onAnimationComplete()
                }
            }
            is UiResult.Error -> {
                launch {
                    crossProgress.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(1000, easing = LinearOutSlowInEasing)
                    )
                    delay(500)
                    onAnimationComplete()
                }
            }
            is UiResult.Idle -> {
                checkProgress.snapTo(0f)
                crossProgress.snapTo(0f)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    color = Color.White,
                    bounded = true,
                ),
                enabled = state == UiResult.Idle
            ) {
                onClick()
            }
    ) {
        when (state) {
            is UiResult.Loading -> {
                Image(
                    painter = painterResource(id = R.drawable.ic_loader),
                    contentDescription = "Loading",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .rotate(rotation)
                        .size(24.dp)
                )
            }
            is UiResult.Success -> {
                Canvas(modifier = Modifier.align(Alignment.Center).size(24.dp)) {
                    val progress = checkProgress.value

                    if (progress <= 1f) {
                        val adjustedProgress = if (progress < 0.5f) progress * 2 else 1f
                        val path1 = Path().apply {
                            moveTo(x = size.width * 0.2f, y = size.height * 0.5f)
                            lineTo(
                                x = size.width * 0.2f + (size.width * 0.2f * adjustedProgress),
                                y = size.height * 0.5f + (size.height * 0.2f * adjustedProgress)
                            )
                        }
                        drawPath(
                            path = path1,
                            color = Color.White,
                            style = Stroke(width = 2.dp.toPx())
                        )
                    }

                    if (progress > 0.5f) {
                        val adjustedProgress = (progress - 0.5f) * 2
                        val path2 = Path().apply {
                            moveTo(x = size.width * 0.4f, y = size.height * 0.7f)
                            lineTo(
                                x = size.width * 0.4f + (size.width * 0.4f * adjustedProgress),
                                y = size.height * 0.7f - (size.height * 0.4f * adjustedProgress)
                            )
                        }
                        drawPath(
                            path = path2,
                            color = Color.White,
                            style = Stroke(width = 2.dp.toPx())
                        )
                    }
                }
            }

            is UiResult.Error -> {
                Canvas(modifier = Modifier.align(Alignment.Center).size(24.dp)) {
                    val progress = crossProgress.value

                    if (progress <= 1f) {
                        val adjustedProgress = if (progress < 0.5f) progress * 2 else 1f
                        val path1 = Path().apply {
                            moveTo(x = size.width * 0.2f, y = size.height * 0.2f)
                            lineTo(
                                x = size.width * 0.2f + (size.width * 0.6f * adjustedProgress),
                                y = size.height * 0.2f + (size.height * 0.6f * adjustedProgress)
                            )
                        }
                        drawPath(
                            path = path1,
                            color = Color.White,
                            style = Stroke(width = 2.dp.toPx())
                        )
                    }

                    if (progress > 0.5f) {
                        val adjustedProgress = (progress - 0.5f) * 2
                        val path2 = Path().apply {
                            moveTo(x = size.width * 0.2f, y = size.height * 0.8f)
                            lineTo(
                                x = size.width * 0.2f + (size.width * 0.6f * adjustedProgress),
                                y = size.height * 0.8f - (size.height * 0.6f * adjustedProgress)
                            )
                        }
                        drawPath(
                            path = path2,
                            color = Color.White,
                            style = Stroke(width = 2.dp.toPx())
                        )
                    }
                }
            }
            is UiResult.Idle -> {
                Text(
                    text = text,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
        }
    }
}