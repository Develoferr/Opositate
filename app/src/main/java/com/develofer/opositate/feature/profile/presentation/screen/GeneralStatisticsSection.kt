package com.develofer.opositate.feature.profile.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutCirc
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.develofer.opositate.R
import com.develofer.opositate.feature.profile.data.model.TestResult
import com.develofer.opositate.feature.profile.presentation.components.ColumnChartComponent
import com.develofer.opositate.feature.profile.presentation.components.LineChartComponent
import com.develofer.opositate.main.components.common.PagerCard
import com.develofer.opositate.main.components.common.StatisticCard
import com.develofer.opositate.ui.theme.Gray700
import com.google.firebase.Timestamp
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.Line

@Composable
fun GeneralStatisticsSection(isDarkTheme: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        StatisticsGeneralSection(isDarkTheme)
        TrendsSection(isDarkTheme)
        AchievementsCard(isDarkTheme)
    }
}

@Composable
fun StatisticsGeneralSection(isDarkTheme: Boolean) {
    Spacer(modifier = Modifier.height(8.dp))
    SectionTitle("Estadísticas Generales")
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        LastTestsCard(isDarkTheme)
        GlobalAveragesCard(isDarkTheme)
    }
}

@Composable
fun LastTestsCard(isDarkTheme: Boolean) {
    val testItems = List(5) { index ->
        TestResult(
            id = "$index",
            testId = index,
            abilityId = 0,
            taskId = 0,
            timestamp = Timestamp.now(),
            questions = emptyList(),
            completionTime = 222,
            maxTime = 0,
            score = 8.34f
        )
    }

    PagerCard(
        items = testItems,
        isDarkTheme = isDarkTheme,
        title = "Últimos Tests",
        showPagerIndicator = true,
        itemContent = { test ->
            Column {
                Text("Test ${test.testId}", fontSize = 14.sp)
                Text("Puntuación: ${test.score}", fontSize = 14.sp)
                Text("Tiempo: ${test.completionTime}", fontSize = 14.sp)
                Text("Aciertos: 12, Errores: 6", fontSize = 14.sp)
                Text("Comparado con anterior: +10%", fontSize = 14.sp)
            }
        }
    )
}

@Composable
fun GlobalAveragesCard(isDarkTheme: Boolean) {
    var showChart by remember { mutableStateOf(false) }

    StatisticCard(
        title = "Promedios Globales",
        titleIcon = painterResource(id = R.drawable.ic_line_chart),
        showChart = showChart,
        isDarkTheme = isDarkTheme,
        onTitleButtonClick = { showChart = !showChart },
        content = {
            Text("• Tiempo promedio por pregunta: 8.5 seg.", fontSize = 14.sp)
            Text("• Puntuación promedio por dificultad:", fontSize = 14.sp)
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                Column {
                    Text("• Fácil: 8.34", fontSize = 11.sp)
                    Text("• Medio-Fácil: 6.75", fontSize = 11.sp)
                    Text(text = "• Medio: 5.00", fontSize = 11.sp)
                }
                Spacer(modifier = Modifier.weight(1f))
                Column {
                    Text("• Medio-difícil: 7.13", fontSize = 11.sp)
                    Text("• Difícil: 5.92", fontSize = 11.sp)
                }
            }

            if (showChart) {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(
                    thickness = 1.dp,
                    color = if (isDarkTheme) Gray700 else Color.DarkGray
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            val gradientColors =
                listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
            AnimatedVisibility(visible = showChart) {
                LineChartComponent(
                    isDarkTheme = isDarkTheme,
                    listOf(
                        Line(
                            label = "Windows",
                            values = listOf(2.80, 1.80, 8.40, 3.50, 5.40),
                            color = Brush.verticalGradient(colors = gradientColors),
                            firstGradientFillColor = Color(0xFF2BC0A1).copy(alpha = .5f),
                            secondGradientFillColor = Color.Transparent,
                            strokeAnimationSpec = tween(2000, easing = EaseInOutCirc),
                            gradientAnimationDelay = 1000,
                            drawStyle = DrawStyle.Stroke(width = 2.dp),
                        )
                    ),
                    minValue = 0.0,
                    maxValue = 10.0,
                    labels = listOf("F", "MF", "M", "MD", "D")
                )
            }
        }
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
            Text(
                "• Evolución de puntuación promedio: +15% en las últimas 5 sesiones.",
                fontSize = 14.sp
            )
            Text("• Puntuación promedio últimas 10 sesiones: 78.", fontSize = 14.sp)
        },
        isDarkTheme = isDarkTheme
    )
}

@Composable
fun TotalTimeCard(isDarkTheme: Boolean) {
    StatisticCard(
        title = "Tiempo Total",
        content = {
            Text("• Tiempo total invertido: 12 horas 35 minutos.", fontSize = 14.sp)
        },
        isDarkTheme = isDarkTheme
    )
}

@Composable
fun AchievementsCard(isDarkTheme: Boolean) {
    SectionTitle("Hitos")
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ProgressCard(isDarkTheme)
    }
}

data class DifficultyData(
    val level: String,
    val percentage: Float
)

@Composable
fun ProgressCard(isDarkTheme: Boolean) {
    var showChart by remember { mutableStateOf(false) }

    val difficultyLevels = listOf(
        DifficultyData("F", 90f),
        DifficultyData("MF", 76f),
        DifficultyData("M", 62f),
        DifficultyData("MD", 69f),
        DifficultyData("D", 50f)
    )

    StatisticCard(
        title = "Progreso",
        isDarkTheme = isDarkTheme,
        showChart = showChart,
        onTitleButtonClick = { showChart = !showChart },
        titleIcon = painterResource(id = R.drawable.ic_bar_chart),
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "• Tests completados por habilidad: ",
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                Column {
                    Text("• Fácil: 90%", fontSize = 11.sp)
                    Text("• Medio-Fácil: 76%", fontSize = 11.sp)
                    Text(text = "• Medio: 62%", fontSize = 11.sp)
                }
                Spacer(modifier = Modifier.weight(1f))
                Column {
                    Text("• Medio-difícil: 69%", fontSize = 11.sp)
                    Text("• Difícil: 50%", fontSize = 11.sp)
                }
            }
            if (showChart) {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(
                    thickness = 1.dp,
                    color = if (isDarkTheme) Gray700 else Color.DarkGray
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            AnimatedVisibility(visible = showChart) {
                val gradientColors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
                val barProperties = BarProperties(
                    thickness = 16.dp,
                    cornerRadius = Bars.Data.Radius.Rectangle(topLeft = 16.dp, topRight = 16.dp)
                )
                ColumnChartComponent(
                    isDarkTheme = isDarkTheme,
                    minValue = 0.0,
                    maxValue = 100.0,
                    data = listOf(
                        Bars(
                            label = "F",
                            values = listOf(
                                Bars.Data(
                                    label = "Linux", value = 70.0,
                                    color = Brush.verticalGradient(colors = gradientColors),
                                    properties = barProperties
                                )
                            )
                        ),
                        Bars(
                            label = "MF",
                            values = listOf(
                                Bars.Data(
                                    label = "Linux", value = 20.0,
                                    color = Brush.verticalGradient(colors = gradientColors),
                                    properties = barProperties
                                )
                            )
                        ),
                        Bars(
                            label = "M",
                            values = listOf(
                                Bars.Data(label = "Linux", value = 66.0,
                                    color = Brush.verticalGradient(colors = gradientColors),
                                    properties = barProperties
                                )
                            )
                        ),
                        Bars(
                            label = "MD",
                            values = listOf(
                                Bars.Data(
                                    label = "Linux", value = 48.0,
                                    color = Brush.verticalGradient(colors = gradientColors),
                                    properties = barProperties
                                )
                            )
                        ),
                        Bars(
                            label = "D",
                            values = listOf(
                                Bars.Data(
                                    label = "Linux", value = 80.0,
                                    color = Brush.verticalGradient(colors = gradientColors),
                                    properties = barProperties
                                )
                            )
                        )
                    )
                )
            }
        }
    )
}


@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}