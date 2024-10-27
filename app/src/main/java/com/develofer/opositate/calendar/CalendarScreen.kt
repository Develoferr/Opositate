package com.develofer.opositate.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.develofer.opositate.main.MainViewModel
import com.develofer.opositate.R

@Composable
fun CalendarScreen(
    navHostController: NavHostController,
    isDarkTheme: Boolean,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val screenTitle = stringResource(id = R.string.calendar_screen__app_bar_title__calendar)
    LaunchedEffect(Unit) { mainViewModel.setAppBarTitle(screenTitle) }
}