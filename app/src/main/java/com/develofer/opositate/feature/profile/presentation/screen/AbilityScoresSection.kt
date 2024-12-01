package com.develofer.opositate.feature.profile.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.develofer.opositate.feature.profile.domain.model.ScoreAbility
import com.develofer.opositate.feature.profile.presentation.components.CustomRadarChart

@Composable
fun AbilityScoresSection(items: List<ScoreAbility> = emptyList()) {

    if (items.isNotEmpty()) {
        val radarLabels = items.map { score -> stringResource(id = score.abilityNameResId) }
        val values = items.map { score -> score.startScore.toDouble() }
        val values2 = items.map { score -> score.presentScore.toDouble() }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CustomRadarChart(
                radarLabels = radarLabels,
                values = values,
                values2 = values2
            )
        }
    }

}