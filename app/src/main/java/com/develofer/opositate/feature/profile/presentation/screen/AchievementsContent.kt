package com.develofer.opositate.feature.profile.presentation.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.develofer.opositate.ui.theme.Gray800
import com.develofer.opositate.ui.theme.Gray960

@Composable
fun AchievementsContent(isDarkTheme: Boolean) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = if (isDarkTheme) CardDefaults.cardElevation(defaultElevation = 0.dp) else CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(1.dp, Gray800, RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) Gray960 else Color.White
        ),
        onClick = {  }
    ) {
        Text(
            modifier = Modifier.padding(start = 18.dp, top = 10.dp, bottom = 10.dp),
            text = "Logros Desbloqueados",
            fontSize = 12.sp
        )
    }
}