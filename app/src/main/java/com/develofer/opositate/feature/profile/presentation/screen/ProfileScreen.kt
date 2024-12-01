package com.develofer.opositate.feature.profile.presentation.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.develofer.opositate.R
import com.develofer.opositate.feature.profile.model.ProfileScreenState
import com.develofer.opositate.feature.profile.presentation.viewmodel.ProfileViewModel
import com.develofer.opositate.main.MainViewModel
import com.develofer.opositate.main.components.common.GradientElevatedAssistChip
import com.develofer.opositate.ui.theme.OpositateTheme

@Composable
fun ProfileScreen(
    isDarkTheme: Boolean,
    mainViewModel: MainViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    SetupSystemUI(mainViewModel)

    val screenState = rememberProfileScreenState(profileViewModel)

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        var selectedTabIndex by remember { mutableIntStateOf(0) }

        TabRow(
            selectedTabIndex,
            isDarkTheme,
            onTabSelected = { index -> selectedTabIndex = index }
        )
        when (selectedTabIndex) {
            0 -> AccountSection(
                isDarkTheme = isDarkTheme,
                userName = screenState.userName,
                userEmail = screenState.userEmail
            )
            1 -> GeneralStatisticsSection(
                isDarkTheme = isDarkTheme
            )
            2 -> ProgressSection(
                scoresByGroup = screenState.scoresByGroup,
                isDarkTheme = isDarkTheme
            )
            3 -> AbilityScoresSection(
                items = screenState.userScores.scores
            )
            4 -> AchievementsSection(
                isDarkTheme = isDarkTheme
            )
        }
    }
}

@Composable
private fun SetupSystemUI(mainViewModel: MainViewModel) {
    mainViewModel.showSystemUI()
    val screenTitle = stringResource(id = R.string.profile_screen__app_bar_title__profile)
    LaunchedEffect(Unit) { mainViewModel.setAppBarTitle(screenTitle) }
}

@Composable
private fun rememberProfileScreenState(profileViewModel: ProfileViewModel): ProfileScreenState {
    val userName by profileViewModel.userName.collectAsState()
    val userEmail by profileViewModel.userEmail.collectAsState()
    val userScores by profileViewModel.scores.collectAsState()
    val scoresByGroup by profileViewModel.scoresByGroup.collectAsState()

    return ProfileScreenState(
        userName = userName,
        userEmail = userEmail,
        userScores = userScores,
        scoresByGroup = scoresByGroup
    )
}

@Composable
private fun TabRow(
    selectedTabIndex: Int,
    isDarkTheme: Boolean,
    onTabSelected: (Int) -> Unit
) {
    val tabTitles = listOf(
        stringResource(id = R.string.profile_screen__title_text__statistics),
        stringResource(id = R.string.profile_screen__title_text__scores),
        stringResource(id = R.string.profile_screen__title_text__chart)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        tabTitles.forEachIndexed { index, title ->
            GradientElevatedAssistChip(
                onClick = { onTabSelected(index) },
                label = title,
                isSelected = selectedTabIndex == index
            )
        }
    }
    Spacer(modifier = Modifier.fillMaxWidth().size(8.dp))
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ProfileScreenPreview() {
    OpositateTheme {
        ProfileScreen(true)
    }
}