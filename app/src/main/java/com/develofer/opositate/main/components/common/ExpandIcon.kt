package com.develofer.opositate.main.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.develofer.opositate.R
import com.develofer.opositate.ui.theme.Gray300

@Composable
fun ExpandIcon(
    isExpanded: Boolean,
    onClick: () -> Unit,
    tint : Color = Gray300
) {
    Icon(
        modifier = Modifier
            .padding(top = 6.dp, end = 10.dp)
            .clickable(onClick = onClick),
        painter = if (isExpanded) painterResource(R.drawable.ic_keyboard_arrow_up)
        else painterResource(R.drawable.ic_keyboard_arrow_down),
        contentDescription = stringResource(
            id = R.string.lesson_screen__content_description__next_month
        ),
        tint = tint
    )
}