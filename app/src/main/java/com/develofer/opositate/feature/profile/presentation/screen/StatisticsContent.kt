package com.develofer.opositate.feature.profile.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.develofer.opositate.ui.theme.Gray900
import com.develofer.opositate.ui.theme.Gray960

@Composable
fun StatisticsContent(isDarkTheme: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        StatisticsGeneralSection(isDarkTheme)
        TrendsSection(isDarkTheme)
        AchievementsSection(isDarkTheme)
    }
}

@Composable
fun StatisticsGeneralSection(isDarkTheme: Boolean) {
    SectionTitle("Estadísticas Generales")
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        LastTestsCard(isDarkTheme)
        GlobalAveragesCard(isDarkTheme)
    }
}

@Composable
fun LastTestsCard(isDarkTheme: Boolean) {
    StatisticCard(
        content = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                val pagerState = rememberPagerState(
                    initialPage = 0,
                    pageCount = { 5 }
                )

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth()
                ) { page ->
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Text("Test ${page + 1}")
                        Text("Puntuación: 80")
                        Text("Tiempo: 5:30 min")
                        Text("Aciertos: 15, Errores: 5")
                        Text("Comparado con anterior: +10%")
                    }
                }

                Row(
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 8.dp, top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    repeat(5) { iteration ->
                        val color = if (pagerState.currentPage == iteration) {
                            if (isDarkTheme) Color.White else Color.Black
                        } else {
                            if (isDarkTheme) Color.DarkGray else Color.LightGray
                        }

                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(color)
                        )
                    }
                }
            }
        },
        isDarkTheme = isDarkTheme
    )
}

@Composable
fun GlobalAveragesCard(isDarkTheme: Boolean) {
    StatisticCard(
        title = "Promedios Globales",
        content = {
            Text("• Puntuación promedio por dificultad: Fácil 85, Media 75, Difícil 65.")
            Text("• Tiempo promedio por pregunta: 8.5 seg.")
        },
        isDarkTheme = isDarkTheme
    )
}

@Composable
fun TrendsSection(isDarkTheme: Boolean) {
    SectionTitle("Tendencias")
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ImprovementTrendCard(isDarkTheme)
        TotalTimeCard(isDarkTheme)
    }
}

@Composable
fun ImprovementTrendCard(isDarkTheme: Boolean) {
    StatisticCard(
        title = "Tendencia de Mejora",
        content = {
            Text("• Evolución de puntuación promedio: +15% en las últimas 5 sesiones.")
            Text("• Puntuación promedio últimas 10 sesiones: 78.")
        },
        isDarkTheme = isDarkTheme
    )
}

@Composable
fun TotalTimeCard(isDarkTheme: Boolean) {
    StatisticCard(
        title = "Tiempo Total",
        content = {
            Text("• Tiempo total invertido: 12 horas 35 minutos.")
        },
        isDarkTheme = isDarkTheme
    )
}

@Composable
fun AchievementsSection(isDarkTheme: Boolean) {
    SectionTitle("Hitos")
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ProgressCard(isDarkTheme)
        UnlockedAchievementsCard(isDarkTheme)
    }
}

@Composable
fun ProgressCard(isDarkTheme: Boolean) {
    StatisticCard(
        title = "Progreso",
        content = {
            Text("• Tests completados por habilidad: 80%.")
            Text("• Tests completados por dificultad: Fácil 90%, Media 75%, Difícil 50%.")
        },
        isDarkTheme = isDarkTheme
    )
}

@Composable
fun UnlockedAchievementsCard(isDarkTheme: Boolean) {
    StatisticCard(
        title = "Logros Desbloqueados",
        content = {
            Text("• Primera habilidad completada.")
            Text("• Todas las tareas de una habilidad resueltas.")
            Text("• Racha de 10 respuestas correctas consecutivas.")
        },
        isDarkTheme = isDarkTheme
    )
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
//        style = MaterialTheme.typography.h5,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
fun StatisticCard(
    title: String = "",
    content: @Composable () -> Unit,
    isDarkTheme: Boolean
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(1.dp, Gray900, RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) Gray960 else Color.LightGray
        )
    ) {
        Column(Modifier.padding(16.dp)) {
            if (title.isNotBlank()) {
                Text(title)
                Spacer(Modifier.height(8.dp))
            }
            content()
        }
    }
}
