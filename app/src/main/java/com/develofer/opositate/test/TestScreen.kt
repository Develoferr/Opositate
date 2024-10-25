package com.develofer.opositate.test

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.develofer.opositate.login.data.AuthRepositoryImpl
import com.develofer.opositate.login.domain.usecase.GetUserUseCase
import com.develofer.opositate.home.components.CustomDualProgressBar
import com.develofer.opositate.home.components.CustomRadarChart
import com.develofer.opositate.main.MainViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun TestScreen(navHostController: NavHostController, mainViewModel: MainViewModel) {
    LaunchedEffect(Unit) { mainViewModel.setAppBarTitle("Test") }
    Column {
        CustomDualProgressBar(
            primaryProgress = 0.6f,
            secondaryProgress = 0.2f
        )
        CustomRadarChart(
            radarLabels = listOf("1", "2", "3", "4", "5", "1", "2", "3", "4", "5"),
            values = listOf(10.0, 8.0, 7.0, 9.0, 10.0, 8.0, 9.0, 6.0, 9.0, 10.0),
            values2 = listOf(5.0, 6.0, 4.0, 4.0, 6.0, 7.0, 7.0, 5.0, 4.0, 5.0)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestScreenPreview() {
    TestScreen(navHostController = rememberNavController(), mainViewModel = MainViewModel(
        GetUserUseCase(AuthRepositoryImpl(FirebaseAuth.getInstance()))
    )
    )
}