package com.develofer.opositate.feature.calendar.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.develofer.opositate.main.MainViewModel
import com.develofer.opositate.R
import com.develofer.opositate.feature.calendar.presentation.components.CalendarContent
import com.develofer.opositate.feature.calendar.presentation.viewmodel.CalendarViewModel

@Composable
fun CalendarScreen(
    navHostController: NavHostController,
    isDarkTheme: Boolean,
    mainViewModel: MainViewModel = hiltViewModel(),
    calendarViewModel: CalendarViewModel = hiltViewModel()
) {
    val screenTitle = stringResource(id = R.string.calendar_screen__app_bar_title__calendar)
    LaunchedEffect(Unit) { mainViewModel.setAppBarTitle(screenTitle) }

    val calendarState by calendarViewModel.uiState.collectAsState()
    CalendarContent (
        yearMonth = calendarState.yearMonth,
        dates = calendarState.dates,
        onDateClick = { date ->

        },
        onPreviousMonthClicked = { calendarViewModel.toPreviousMonth() },
        onNextMonthClicked = { calendarViewModel.toNextMonth() },
        isDarkTheme = isDarkTheme
    )
}