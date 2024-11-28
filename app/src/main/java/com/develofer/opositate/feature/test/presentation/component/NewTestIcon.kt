package com.develofer.opositate.feature.test.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.develofer.opositate.R


@Composable
fun NewTestIcon(onClick: () -> Unit) {
    Icon(
        modifier = Modifier.size(24.dp)
            .clickable { onClick() },
        painter = painterResource(id = R.drawable.ic_add),
        contentDescription = "add questionary",
        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
    )
}