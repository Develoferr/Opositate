package com.develofer.opositate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.develofer.opositate.presentation.screen.HomeScreen
import com.develofer.opositate.presentation.screen.LoginScreen
import com.develofer.opositate.presentation.screen.RegisterScreen
import com.develofer.opositate.presentation.navigation.AppRoutes.Destination
import com.develofer.opositate.presentation.viewmodel.MainViewModel

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    startDestination: String,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    NavHost(navHostController, startDestination = startDestination) {
        composable(Destination.LOGIN.route) { LoginScreen(navHostController, mainViewModel) }
        composable(Destination.REGISTER.route) { RegisterScreen(navHostController) }
        composable(Destination.HOME.route) { HomeScreen(navHostController, mainViewModel) }
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