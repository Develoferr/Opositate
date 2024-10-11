package com.develofer.opositate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.develofer.opositate.presentation.screen.HomeScreen
import com.develofer.opositate.presentation.screen.LoginScreen
import com.develofer.opositate.presentation.screen.RegisterScreen

@Composable
fun AppNavigation(navHostController: NavHostController) {

    NavHost(navHostController, startDestination = "login") {
        composable("login") { LoginScreen(navHostController) }
        composable("register") { RegisterScreen(navHostController) }
        composable("home") { HomeScreen(navHostController) }
    }

}

fun navigateToHome(navController: NavHostController) {
    navController.navigate("home") {
        popUpTo("login") { inclusive = true }
        launchSingleTop = true
    }
}

fun navigateToLogin(navController: NavHostController) {
    navController.navigate("login") {
        popUpTo("home") { inclusive = true }
        launchSingleTop = true
    }
}