package com.develofer.opositate.profile.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.aay.compose.radarChart.RadarChart
import com.aay.compose.radarChart.model.NetLinesStyle
import com.aay.compose.radarChart.model.Polygon
import com.aay.compose.radarChart.model.PolygonStyle
import com.develofer.opositate.ui.theme.OpositateTheme

@Composable
fun CustomRadarChart(radarLabels: List<String>, values: List<Double>, values2: List<Double>) {
    val labelsStyle = TextStyle(
        color = MaterialTheme.colorScheme.onBackground,
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    )

    val scalarValuesStyle = TextStyle(
        color = MaterialTheme.colorScheme.onBackground,
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    )

    RadarChart(
        modifier = Modifier.fillMaxSize(),
        radarLabels = radarLabels,
        labelsStyle = labelsStyle,
        netLinesStyle = NetLinesStyle(
            netLineColor = MaterialTheme.colorScheme.secondaryContainer,
            netLinesStrokeWidth = 2f,
            netLinesStrokeCap = StrokeCap.Butt,
        ),
        scalarSteps = 11,
        scalarValue = 10.0,
        scalarValuesStyle = scalarValuesStyle,
        polygons = listOf(
            Polygon(
                values = values,
                unit = "",
                style = PolygonStyle(
                    fillColor = MaterialTheme.colorScheme.primary,
                    fillColorAlpha = 0.3f,
                    borderColor = MaterialTheme.colorScheme.primary,
                    borderColorAlpha = 0.8f,
                    borderStrokeWidth = 5f,
                    borderStrokeCap = StrokeCap.Round,
                )
            ),
            Polygon(
                values = values2,
                unit = "",
                style = PolygonStyle(
                    fillColor = MaterialTheme.colorScheme.secondary,
                    fillColorAlpha = 0.3f,
                    borderColor = MaterialTheme.colorScheme.secondary,
                    borderColorAlpha = 1f,
                    borderStrokeWidth = 5f,
                    borderStrokeCap = StrokeCap.Square
                )
            )
        )
    )
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun CustomRadarChartPreview() {
    OpositateTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            CustomRadarChart(
                radarLabels = listOf("1", "2", "3", "4", "5", "1", "2", "3", "4", "5"),
                values = listOf(10.0, 8.0, 7.0, 9.0, 10.0, 8.0, 9.0, 6.0, 9.0, 10.0),
                values2 = listOf(5.0, 6.0, 4.0, 4.0, 6.0, 7.0, 7.0, 5.0, 4.0, 5.0)
            )
        }

    }
}