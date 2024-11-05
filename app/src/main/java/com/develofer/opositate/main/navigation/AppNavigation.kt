package com.develofer.opositate.main.navigation

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
import androidx.navigation.toRoute
import com.develofer.opositate.feature.calendar.presentation.screen.CalendarScreen
import com.develofer.opositate.feature.lesson.LessonScreen
import com.develofer.opositate.feature.login.presentation.screen.LoginScreen
import com.develofer.opositate.feature.login.presentation.screen.RegisterScreen
import com.develofer.opositate.feature.profile.presentation.screen.ProfileScreen
import com.develofer.opositate.feature.test.TestScreen
import com.develofer.opositate.feature.test.TestSolvingScreen
import com.develofer.opositate.main.MainViewModel
import com.develofer.opositate.main.components.CustomAppBar

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    startDestination: Route,
    mainViewModel: MainViewModel = hiltViewModel(),
    appBarTitle: State<String>,
    isDarkTheme: Boolean
) {
    val currentRoute = navHostController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
        bottomBar =  {
            if (currentRoute in listOf(
                    Profile.route,
                    Test.route,
                    Lesson.route,
                    Calendar.route
                )
            ) {
                CustomBottomNavigationBar(navHostController)
            }
        },
        topBar = {
            if (currentRoute in listOf(
                    Profile.route,
                    Test.route,
                    Lesson.route,
                    Calendar.route
                )
            ) {
                CustomAppBar(
                    title = appBarTitle,
                    isDarkTheme = isDarkTheme,
                    logout = {
                        mainViewModel.logout()
                        navigateToLogin(navHostController)
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navHostController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<Login> {
                LoginScreen(
                    navigateToRegister = { navHostController.navigate(Register) },
                    navigateToProfile = { navigateToProfile(navHostController) },
                    isDarkTheme = isDarkTheme,
                    mainViewModel = mainViewModel
                )
            }
            composable<Register> {
                RegisterScreen(
                    navigateToLogin = { navigateToLogin(navHostController) },
                    isDarkTheme = isDarkTheme
                )
            }
            composable<Profile> {
                ProfileScreen(
                    isDarkTheme = isDarkTheme,
                    mainViewModel = mainViewModel
                )
            }
            composable<Test> {
                TestScreen(
                    navigateToTestSolving = { testId -> navHostController.navigate(TestSolving(testId))},
                    isDarkTheme = isDarkTheme,
                    mainViewModel = mainViewModel
                )
            }
            composable<Lesson> {
                LessonScreen(
                    isDarkTheme = isDarkTheme,
                    mainViewModel = mainViewModel
                )
            }
            composable<Calendar> {
                CalendarScreen(
                    isDarkTheme = isDarkTheme,
                    mainViewModel = mainViewModel
                )
            }
            composable<TestSolving> { backStackEntry ->
                val testSolving: TestSolving = backStackEntry.toRoute()
                TestSolvingScreen(
                    testId = testSolving.testId
                )
            }
        }
    }
}

private fun navigateToProfile(navController: NavHostController) {
    navController.navigate(Profile) {
        popUpTo(navController.graph.startDestinationId) { inclusive = true }
        launchSingleTop = true
    }
}

private fun navigateToLogin(navController: NavHostController) {
    navController.navigate(Login) {
        popUpTo(navController.graph.startDestinationId) { inclusive = true }
        launchSingleTop = true
    }
}