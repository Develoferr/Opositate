package com.develofer.opositate.test

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.develofer.opositate.main.MainViewModel
import com.develofer.opositate.R

@Composable
fun TestScreen(navHostController: NavHostController, mainViewModel: MainViewModel) {
    val screenTitle = stringResource(id = R.string.test_screen__app_bar_title__test)
    LaunchedEffect(Unit) { mainViewModel.setAppBarTitle(screenTitle) }
    Spacer(modifier = Modifier.size(24.dp))
    val testsList: List<TestItem> = listOf(
        TestItem(1, "Test", false),
        TestItem(2, "Test 1", true),
        TestItem(3, "Test 2", false),
        TestItem(4, "Test 3", false),
        TestItem(5, "Test 4", false),
        TestItem(6, "Test 5", false),
        TestItem(7, "Test 6", false),
        TestItem(8, "Test 7", false),
        TestItem(9, "Test 8", false),
        TestItem(10, "Test 9", false),
        TestItem(11, "Test 10", false),
        TestItem(12, "Test 11", false),
        TestItem(13, "Test 12", false),
        TestItem(14, "Test 13", false),
    )
    StudyItemList(testsList)
    Spacer(modifier = Modifier.size(32.dp))
}

@Preview(showBackground = true)
@Composable
fun TestScreenPreview() {
    TestScreen(rememberNavController(), hiltViewModel())
}