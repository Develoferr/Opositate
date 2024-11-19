package com.develofer.opositate.main.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.develofer.opositate.main.navigation.Route
import com.develofer.opositate.ui.theme.Gray900
import kotlin.math.roundToInt

@Composable
fun AnimatedSelectionIndicator(
    currentRoute: String?,
    items: List<Route>,
    colors: List<Color>
) {
    var totalWidth by remember { mutableIntStateOf(0) }
    val itemWidth = totalWidth / 5f
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged { totalWidth = it.width }
            .background(Gray900)
    ) {
        val transition = updateTransition(targetState = currentRoute, label = "indicator")
        val indicatorOffset by transition.animateFloat(
            label = "indicatorOffset",
            transitionSpec = {
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            }
        ) { route ->
            val index = items.indexOfFirst { it.route == route }
            if (index >= 0) index * itemWidth
            else 0f
        }

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
        ) {
            drawLine(
                color = Gray900,
                start = Offset(0f, -(size.height / 4)),
                end = Offset(size.width, -(size.height / 4)),
                strokeWidth = size.height / 2
            )
        }

        Canvas(
            modifier = Modifier
                .width(with(LocalDensity.current) { itemWidth.toDp() })
                .height(3.dp)
                .offset { IntOffset(indicatorOffset.roundToInt(), 0) }
        ) {
            drawLine(
                brush = Brush.linearGradient(
                    colors = colors
                ),
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = size.height
            )
        }
    }
}