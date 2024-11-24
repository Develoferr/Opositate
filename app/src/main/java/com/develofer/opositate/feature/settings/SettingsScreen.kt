package com.develofer.opositate.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.develofer.opositate.R
import com.develofer.opositate.main.MainViewModel

@Composable
fun SettingsScreen(
    isDarkTheme: Boolean,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    mainViewModel.showSystemUI()
    val screenTitle = stringResource(id = R.string.settings_screen__app_bar_title__settings)
    LaunchedEffect(Unit) { mainViewModel.setAppBarTitle(screenTitle) }
}