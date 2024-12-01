package com.develofer.opositate.feature.profile.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.develofer.opositate.ui.theme.Gray800
import com.develofer.opositate.ui.theme.Gray960

@Composable
fun OpositateCard(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    content: @Composable () -> Unit,
    onClick: () -> Unit = { }
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = if (isDarkTheme) CardDefaults.cardElevation(defaultElevation = 0.dp) else CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(1.dp, Gray800, RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) Gray960 else Color.White
        ),
        onClick = { onClick() }
    ) {
        content()
    }
}

@Composable
fun OpositateCardWithoutClick(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    content: @Composable () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = if (isDarkTheme) CardDefaults.cardElevation(defaultElevation = 0.dp) else CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(1.dp, Gray800, RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) Gray960 else Color.White
        )
    ) {
        content()
    }
}