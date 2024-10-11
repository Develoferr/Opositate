package com.develofer.opositate.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.develofer.opositate.presentation.viewmodel.HomeViewModel
import com.develofer.opositate.ui.theme.OpositateTheme

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to the Home Screen!")

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                homeViewModel.logout()
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            }
        ) {
            Text(text = "Log Out")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    OpositateTheme {
        HomeScreen(rememberNavController())
    }
}
//