package com.develofer.opositate.presentation.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.develofer.opositate.presentation.main.MainViewModel

@Composable
fun TestScreen(navHostController: NavHostController, mainViewModel: MainViewModel) {
    LaunchedEffect(Unit) { mainViewModel.setAppBarTitle("Test") }
}