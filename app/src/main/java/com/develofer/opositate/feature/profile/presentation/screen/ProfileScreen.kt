package com.develofer.opositate.feature.profile.presentation.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.develofer.opositate.R
import com.develofer.opositate.feature.login.presentation.component.CustomBodyText
import com.develofer.opositate.feature.profile.domain.model.ScoreVO
import com.develofer.opositate.feature.profile.presentation.components.CustomDualProgressBar
import com.develofer.opositate.feature.profile.presentation.components.CustomRadarChart
import com.develofer.opositate.feature.profile.presentation.viewmodel.ProfileViewModel
import com.develofer.opositate.main.MainViewModel
import com.develofer.opositate.ui.theme.OpositateTheme

@Composable
fun ProfileScreen(
    isDarkTheme: Boolean,
    mainViewModel: MainViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    mainViewModel.showSystemUI()
    val screenTitle = stringResource(id = R.string.profile_screen__app_bar_title__profile)
    LaunchedEffect(Unit) { mainViewModel.setAppBarTitle(screenTitle) }

    val userName by profileViewModel.userName.collectAsState()
    val userScores by profileViewModel.scores.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomBodyText(text = userName, isDarkTheme = isDarkTheme, textSize = 25.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.profile_screen__text__level, userScores.level))
        Spacer(modifier = Modifier.height(16.dp))
        val tabTitles = listOf(
            stringResource(id = R.string.profile_screen__title_text__scores),
            stringResource(id = R.string.profile_screen__title_text__chart)
        )
        var selectedTabIndex by remember { mutableIntStateOf(0) }

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.fillMaxSize()) {
             TabRow(selectedTabIndex = selectedTabIndex) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }
            when (selectedTabIndex) {
                0 -> ScoresContent(userScores.scores)
                1 -> ChartContent(userScores.scores)
            }
        }
    }
}

@Composable
fun ScoresContent(items: List<ScoreVO> = emptyList()) {
    Spacer(modifier = Modifier.size(16.dp))
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(items.size) {
            val score = items[it]
            Text(text = stringResource(id = score.abilityName))
            Spacer(modifier = Modifier.size(0.dp))
            CustomDualProgressBar(
                primaryProgress = ( score.startScore.toFloat() / 10 ),
                secondaryProgress = ( score.presentScore.toFloat() / 10 )
            )
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}

@Composable
fun ChartContent(items: List<ScoreVO> = emptyList()) {
    if (items.isNotEmpty()) {
        val radarLabels = items.map { score -> stringResource(id = score.abilityName) }
        val values = items.map { score -> score.startScore.toDouble() }
        val values2 = items.map { score -> score.presentScore.toDouble() }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            CustomRadarChart(
                radarLabels = radarLabels,
                values = values,
                values2 = values2
            )
        }
    }

}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ProfileScreenPreview() {
    OpositateTheme {
        ProfileScreen(true)
    }
}