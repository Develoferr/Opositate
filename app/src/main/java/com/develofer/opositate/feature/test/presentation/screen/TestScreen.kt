package com.develofer.opositate.feature.test.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.develofer.opositate.R
import com.develofer.opositate.feature.test.presentation.component.TestContent
import com.develofer.opositate.feature.test.presentation.viewmodel.TestViewModel
import com.develofer.opositate.main.MainViewModel
import com.develofer.opositate.main.components.common.SuccessDialog

@Composable
fun TestScreen(
    navigateToTestSolving: (testId: String, abilityId: Int, taskId: Int) -> Unit,
    isDarkTheme: Boolean,
    mainViewModel: MainViewModel = hiltViewModel(),
    testViewModel: TestViewModel = hiltViewModel()
) {
    mainViewModel.showSystemUI()
    val screenTitle = stringResource(id = R.string.test_screen__app_bar_title__test)
    var showNewTestDialog by remember { mutableStateOf(false) }
    var selectedTestId by remember { mutableStateOf("0") }
    var selectedAbilityId by remember { mutableIntStateOf(0) }
    var selectedTaskId by remember { mutableIntStateOf(0) }
    var newTestName by remember { mutableStateOf("") }

    LaunchedEffect(Unit) { mainViewModel.setAppBarTitle(screenTitle) }

    val testList by testViewModel.testAsks.collectAsState()
    TestContent(
        asksByGroup = testList,
        onClickItem = { abilityId, taskId, taskName ->
            selectedAbilityId = abilityId
            selectedTaskId = taskId
            newTestName = taskName
            showNewTestDialog = true
        },
        isDarkTheme = isDarkTheme
    )
    if (showNewTestDialog) {
        SuccessDialog(
            modifier = Modifier.clip(shape = RoundedCornerShape(13.dp)),
            isDialogVisible = showNewTestDialog,
            onDismiss = { showNewTestDialog = false },
            title = { Text(text = "Empempezar nuevo test") },
            content = {
                val options = listOf(
                    "Fácil" to 0,
                    "Medio-fácil" to 1,
                    "Media" to 2,
                    "Medio-difícil" to 3,
                    "Difícil" to 4
                )
                Column {
                    Text(
                        text = buildAnnotatedString {
                            append("Vas a comenzar un test sobre ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(newTestName)
                            }
                        },
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(text = "Elige la dificultad del test")
                    Spacer(modifier = Modifier.padding(8.dp))
                    options.forEach { (label, id) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedTestId = id.toString()
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedTestId.toInt() == id,
                                onClick = {
                                    selectedTestId = id.toString()
                                }
                            )
                            Text(
                                text = label,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }

            },
            confirmButton = {
                TextButton(onClick = {
                    showNewTestDialog = false
                    mainViewModel.startProgress {
                        navigateToTestSolving(selectedTestId, selectedAbilityId, selectedTaskId)
                    }
                }) {
                    Text(
                        "Empezar"
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showNewTestDialog = false }) {
                    Text(
                        "Cancelar"
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestScreenPreview() {
    TestScreen({ _, _, _ -> }, isDarkTheme = true)
}