package com.develofer.opositate.presentation.lesson

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.develofer.opositate.ui.main.MainViewModel

@Composable
fun LessonScreen(navController: NavHostController, mainViewModel: MainViewModel) {
    LaunchedEffect(Unit) { mainViewModel.setAppBarTitle("Lesson") }
}