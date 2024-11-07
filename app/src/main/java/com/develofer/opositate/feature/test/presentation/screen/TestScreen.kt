package com.develofer.opositate.feature.test.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.develofer.opositate.R
import com.develofer.opositate.feature.test.presentation.component.StudyItemList
import com.develofer.opositate.feature.test.presentation.viewmodel.TestViewModel
import com.develofer.opositate.main.MainViewModel

@Composable
fun TestScreen(
    navigateToTestSolving: (testId: String) -> Unit,
    isDarkTheme: Boolean,
    mainViewModel: MainViewModel = hiltViewModel(),
    testViewModel: TestViewModel = hiltViewModel()
) {
    val screenTitle = stringResource(id = R.string.test_screen__app_bar_title__test)
    LaunchedEffect(Unit) { mainViewModel.setAppBarTitle(screenTitle) }

    val testList by testViewModel.tests.collectAsState()
    StudyItemList(
        studyItemList = testList, 
        isDarkTheme = isDarkTheme,
        onClickItem = { testId ->
            navigateToTestSolving(testId)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TestScreenPreview() {
    TestScreen({}, isDarkTheme = true)
}