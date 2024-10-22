package com.develofer.opositate.presentation.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.develofer.opositate.presentation.main.MainViewModel

@Composable
fun CalendarScreen(navHostController: NavHostController, mainViewModel: MainViewModel) {
    LaunchedEffect(Unit) { mainViewModel.setAppBarTitle("Calendar") }
}