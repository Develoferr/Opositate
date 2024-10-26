package com.develofer.opositate.lesson

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.develofer.opositate.main.MainViewModel
import com.develofer.opositate.R


@Composable
fun LessonScreen(navController: NavHostController, mainViewModel: MainViewModel) {
    val screenTitle = stringResource(id = R.string.lesson_screen__app_bar_title__lesson)
    LaunchedEffect(Unit) { mainViewModel.setAppBarTitle(screenTitle) }
}