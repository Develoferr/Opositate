package com.develofer.opositate.feature.settings.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.develofer.opositate.ui.theme.Gray800
import com.develofer.opositate.ui.theme.Gray900
import com.develofer.opositate.ui.theme.Primary500

@Composable
fun SettingsAssistChip(
    selected: Boolean,
    onWeekStartChanged: (Boolean) -> Unit,
    text: String
) {
    AssistChip(
        label = { Text(text) },
        onClick = { onWeekStartChanged(true) },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = if (selected) Gray900 else Color.Transparent,
            labelColor =  if (selected) Primary500 else Gray800
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (selected) Gray800 else Gray800
        )
    )
}