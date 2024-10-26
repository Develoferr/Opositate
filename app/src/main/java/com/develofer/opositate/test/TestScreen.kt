package com.develofer.opositate.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.develofer.opositate.login.data.AuthRepositoryImpl
import com.develofer.opositate.login.domain.usecase.GetUserUseCase
import com.develofer.opositate.main.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.develofer.opositate.R

@Composable
fun TestScreen(navHostController: NavHostController, mainViewModel: MainViewModel) {
    val screenTitle = stringResource(id = R.string.test_screen__app_bar_title__test)
    LaunchedEffect(Unit) { mainViewModel.setAppBarTitle(screenTitle) }
}

@Preview(showBackground = true)
@Composable
fun TestScreenPreview() {
    TestScreen(navHostController = rememberNavController(), mainViewModel = MainViewModel(
        GetUserUseCase(AuthRepositoryImpl(FirebaseAuth.getInstance()))
    )
    )
}