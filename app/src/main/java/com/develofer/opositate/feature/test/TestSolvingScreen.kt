package com.develofer.opositate.feature.test

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.develofer.opositate.R
import com.develofer.opositate.feature.profile.Question
import com.develofer.opositate.feature.profile.Test
import com.develofer.opositate.ui.theme.OpositateTheme
import com.develofer.opositate.utils.Constants.TWO_DIGITS_FORMAT
import kotlinx.coroutines.delay
import java.util.Locale
import kotlin.time.Duration.Companion.seconds

@Composable
fun TestSolvingScreen(test: Test) {
    val testSize = test.questions.size
    val maxTime = test.maxTime
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var showStartDialog by remember { mutableStateOf(true) }
    var timeCount by remember { mutableIntStateOf(0) }
    var isTestActive by remember { mutableStateOf(false) }

    val condition = if (maxTime == 0) true
                    else timeCount < maxTime

    LaunchedEffect(isTestActive) {
        if (isTestActive) {
            while (condition) {
                delay(1.seconds)
                timeCount++
            }
            if (timeCount == test.maxTime) isTestActive = false
        }
    }

    if (showStartDialog) {
        Dialog(onDismissRequest = { }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.test_solving_screen__text_title).uppercase(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = stringResource(R.string.test_solving_screen__text_description),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Button(
                        onClick = {
                            showStartDialog = false
                            isTestActive = true
                        }
                    ) {
                        Text(stringResource(R.string.test_solving_screen__text_btn__start).uppercase())
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${currentQuestionIndex + 1}/${testSize}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = R.string.test_solving_screen__text__time,
                    String.format(Locale.getDefault(), TWO_DIGITS_FORMAT, timeCount / 60),
                    String.format(Locale.getDefault(), TWO_DIGITS_FORMAT, timeCount % 60)
                ),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (test.maxTime == 0) MaterialTheme.colorScheme.primary
                        else
                            if (timeCount > (test.maxTime * 0.84)) Color.Red
                            else MaterialTheme.colorScheme.primary
            )
        }

        val currentQuestion = test.questions[currentQuestionIndex]
        Text(
            text = currentQuestion.question,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            textAlign = TextAlign.Center
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            currentQuestion.options.forEachIndexed { index, option ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = currentQuestion.selectedAnswer == index,
                            onClick = { currentQuestion.selectedAnswer = index }
                        ),
                    border = BorderStroke(
                        1.dp,
                        if (currentQuestion.selectedAnswer == index) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline
                    )
                ) {
                    Text(
                        text = option,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (currentQuestionIndex > 0) {
                IconButton(onClick = { currentQuestionIndex-- }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack,
                        stringResource(R.string.test_solving_screen__content_description_before_button))
                }
            } else {
                Spacer(modifier = Modifier.width(48.dp))
            }

            if (currentQuestionIndex == testSize - 1) {
                Button(
                    onClick = {
                        isTestActive = false
                    }
                ) {
                    Text(stringResource(R.string.test_solving_screen__text_btn__finish).uppercase())
                }
            }

            if (currentQuestionIndex < testSize - 1) {
                IconButton(onClick = { currentQuestionIndex++ }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward,
                        stringResource(R.string.test_solving_screen__content_description__after_button))
                }
            } else {
                Spacer(modifier = Modifier.width(48.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PsychometricTestScreenPreview() {
    OpositateTheme { TestSolvingScreen(
        test = Test(
            id = 1,
            questions = listOf(
                Question(
                    id = 1,
                    question = "Question 1",
                    options = listOf("Option 1", "Option 2", "Option 3", "Option 4"),
                    correctAnswer = 1
                ),
                Question(
                    id = 2,
                    question = "Question 2",
                    options = listOf("Option 1", "Option 2", "Option 3", "Option 4"),
                    correctAnswer = 2
                ),
                Question(
                    id = 3,
                    question = "Question 3",
                    options = listOf("Option 1", "Option 2", "Option 3", "Option 4"),
                    correctAnswer = 3
                ),
                Question(
                    id = 4,
                    question = "Question 4",
                    options = listOf("Option 1", "Option 2", "Option 3", "Option 4"),
                    correctAnswer = 4
                ),
                Question(
                    id = 5,
                    question = "Question 5",
                    options = listOf("Option 1", "Option 2", "Option 3", "Option 4"),
                    correctAnswer = 1
                )
            ),
            name = "Test 1"
        )
    ) }
}