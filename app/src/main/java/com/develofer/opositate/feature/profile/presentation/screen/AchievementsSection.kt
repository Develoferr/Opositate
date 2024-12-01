package com.develofer.opositate.feature.profile.presentation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.develofer.opositate.main.components.common.StatisticCard

@Composable
fun AchievementsSection(isDarkTheme: Boolean) {
    StatisticCard(
        title = "Logros Desbloqueados",
        content = {
            Text("• Primera habilidad completada.", fontSize = 14.sp)
            Text("• Todas las tareas de una habilidad resueltas.", fontSize = 14.sp)
            Text("• Racha de 10 respuestas correctas consecutivas.", fontSize = 14.sp)
        },
        isDarkTheme = isDarkTheme
    )
}

