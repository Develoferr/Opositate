package com.develofer.opositate.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.develofer.opositate.presentation.calendar.CalendarScreen
import com.develofer.opositate.ui.custom.CustomAppBar
import com.develofer.opositate.ui.custom.CustomBottomNavigationBar
import com.develofer.opositate.presentation.home.HomeScreen
import com.develofer.opositate.presentation.lesson.LessonScreen
import com.develofer.opositate.presentation.login.screen.LoginScreen
import com.develofer.opositate.presentation.login.screen.RegisterScreen
import com.develofer.opositate.ui.navigation.AppRoutes.Destination
import com.develofer.opositate.ui.main.MainViewModel
import com.develofer.opositate.presentation.test.TestScreen

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    startDestination: String,
    mainViewModel: MainViewModel = hiltViewModel(),
    appBarTitle: State<String>,
    isDarkTheme: Boolean
) {
    val currentRoute = navHostController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
        bottomBar =  {
            if (currentRoute in listOf(
                    Destination.HOME.route,
                    Destination.TEST.route,
                    Destination.LESSON.route,
                    Destination.CALENDAR.route
                )
            ) {
                CustomBottomNavigationBar(navHostController)
            }
        },
        topBar = {
            if (currentRoute in listOf(
                    Destination.HOME.route,
                    Destination.TEST.route,
                    Destination.LESSON.route,
                    Destination.CALENDAR.route
                )
            ) {
                CustomAppBar(appBarTitle, isDarkTheme)
            }
        }
    ) { paddingValues ->
        NavHost(
            navHostController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Destination.LOGIN.route) { LoginScreen(navHostController, mainViewModel) }
            composable(Destination.REGISTER.route) { RegisterScreen(navHostController) }
            composable(Destination.HOME.route) { HomeScreen(navHostController, mainViewModel) }
            composable(Destination.TEST.route) { TestScreen(navHostController, mainViewModel) }
            composable(Destination.LESSON.route) { LessonScreen(navHostController, mainViewModel) }
            composable(Destination.CALENDAR.route) { CalendarScreen(navHostController, mainViewModel) }
        }
    }
}

fun navigateToHome(navController: NavHostController) {
    navController.navigate(Destination.HOME.route) {
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
    }
}

fun navigateToLogin(navController: NavHostController) {
    navController.navigate(Destination.LOGIN.route) {
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
    }
}