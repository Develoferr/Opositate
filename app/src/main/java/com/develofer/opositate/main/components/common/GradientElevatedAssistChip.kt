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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.develofer.opositate.ui.theme.Gray900
import com.develofer.opositate.ui.theme.Gray960
import com.develofer.opositate.ui.theme.Secondary

@Composable
fun GradientElevatedAssistChip(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: String? = null,
    isSelected: Boolean,
    isDarkTheme: Boolean,
    leadingIcon: @Composable (() -> Unit)? = null
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
        leadingIcon = leadingIcon,
        onClick = onClick,
        modifier = modifier,
        label = {
            if (label != null) {
                Text(
                    text = label.uppercase(),
                    fontSize = 10.sp,
                    color = when {
                        isDarkTheme && isSelected -> Color.Unspecified
                        isDarkTheme && !isSelected -> MaterialTheme.colorScheme.onSurface
                        !isDarkTheme && isSelected -> Color.White
                        else -> Gray900
                    },
                    fontWeight = if (!isDarkTheme) FontWeight.W400 else FontWeight.Normal,
                    modifier = if (isDarkTheme) textStyle else Modifier
                )
            }
        },
        colors = AssistChipDefaults.elevatedAssistChipColors(
            containerColor = when {
                isDarkTheme && isSelected -> Color.Black
                isDarkTheme && !isSelected -> Gray960
                !isDarkTheme && isSelected -> Secondary
                else -> Color.White
            }
        ),
        border = when {
            isDarkTheme && isSelected -> BorderStroke(width = 2.dp, brush = borderBrush)
            isDarkTheme && !isSelected -> BorderStroke(1.dp, Gray900)
            !isDarkTheme && !isSelected -> BorderStroke(2.dp, Secondary)
            else -> BorderStroke(width = 0.dp, color = Color.Transparent)
        }
    )
}