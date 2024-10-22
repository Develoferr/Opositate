package com.develofer.opositate.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.develofer.opositate.presentation.calendar.CalendarScreen
import com.develofer.opositate.presentation.home.BottomNavigationBar
import com.develofer.opositate.presentation.home.HomeScreen
import com.develofer.opositate.presentation.lesson.LessonScreen
import com.develofer.opositate.presentation.login.screen.LoginScreen
import com.develofer.opositate.presentation.register.screen.RegisterScreen
import com.develofer.opositate.presentation.navigation.AppRoutes.Destination
import com.develofer.opositate.presentation.main.MainViewModel
import com.develofer.opositate.presentation.test.TestScreen

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    startDestination: String,
    mainViewModel: MainViewModel = hiltViewModel()
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
                BottomNavigationBar(navHostController)
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
            composable(Destination.TEST.route) { TestScreen(navHostController) }
            composable(Destination.LESSON.route) { LessonScreen(navHostController) }
            composable(Destination.CALENDAR.route) { CalendarScreen(navHostController) }
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