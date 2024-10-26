package com.develofer.opositate.profile.presentation.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.develofer.opositate.R
import com.develofer.opositate.profile.components.CustomDualProgressBar
import com.develofer.opositate.profile.components.CustomRadarChart
import com.develofer.opositate.profile.presentation.model.ScoreData
import com.develofer.opositate.profile.presentation.viewmodel.ProfileViewModel
import com.develofer.opositate.main.MainViewModel
import com.develofer.opositate.ui.theme.OpositateTheme

@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    mainViewModel.showSystemUI()
    val screenTitle = stringResource(id = R.string.profile_screen__app_bar_title__profile)
    LaunchedEffect(Unit) { mainViewModel.setAppBarTitle(screenTitle) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val tabTitles = listOf(
            stringResource(id = R.string.profile_screen__title_text__scores),
            stringResource(id = R.string.profile_screen__title_text__chart)
        )
        var selectedTabIndex by remember { mutableIntStateOf(0) }

        val items = listOf(
            ScoreData("Habilidad 1", 0.6f, 0.2f),
            ScoreData("Habilidad 2", 0.5f, 0.1f),
            ScoreData("Habilidad 3", 0.7f, 0.3f),
            ScoreData("Habilidad 4", 0.5f, 0.2f),
            ScoreData("Habilidad 5", 0.8f, 0.2f),
            ScoreData("Habilidad 6", 0.6f, 0.1f),
        )

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
                0 -> ScoresContent(items)
                1 -> ChartContent(items)
            }
        }
    }
}

@Composable
fun ScoresContent(items: List<ScoreData> = emptyList()) {
    Spacer(modifier = Modifier.size(16.dp))
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(items.size) {
            Text(text = items[it].ability)
            Spacer(modifier = Modifier.size(0.dp))
            CustomDualProgressBar(
                primaryProgress = items[it].primaryProgress,
                secondaryProgress = items[it].secondaryProgress
            )
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}

@Composable
fun ChartContent(items: List<ScoreData> = emptyList()) {
    val radarLabels = items.map { it.ability }
    val values = items.map { (it.primaryProgress * 10).toDouble() }
    val values2 = items.map { (it.secondaryProgress * 10).toDouble() }
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

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ProfileScreenPreview() {
    OpositateTheme {
        ProfileScreen(rememberNavController())
    }
}