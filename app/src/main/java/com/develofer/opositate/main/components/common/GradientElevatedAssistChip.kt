package com.develofer.opositate.main.components.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.develofer.opositate.ui.theme.Gray900
import com.develofer.opositate.ui.theme.Gray960


@Composable
fun GradientElevatedAssistChip(
    onClick: () -> Unit,
    label: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)

    val textStyle = if (isSelected) {
        Modifier
            .graphicsLayer(alpha = 0.99f)
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawRect(
                        brush = Brush.linearGradient(
                            colors = colors,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, 0f)
                        ),
                        blendMode = BlendMode.SrcAtop
                    )
                }
            }
    } else {
        Modifier
    }

    val borderBrush = Brush.linearGradient(
        colors = colors,
        start = Offset(0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 0f)
    )

    ElevatedAssistChip(
        onClick = onClick,
        modifier = modifier,
        label = {
            Text(
                text = label.uppercase(),
                fontSize = 10.sp,
                modifier = textStyle,
                color = if (isSelected) Color.Unspecified else MaterialTheme.colorScheme.onSurface
            )
        },
        colors = AssistChipDefaults.elevatedAssistChipColors(
            containerColor = if (isSelected) Color.Black else Gray960
        ),
        border = if (isSelected) BorderStroke(width = 2.dp, brush = borderBrush)
        else BorderStroke(1.dp, Gray900)
    )
}