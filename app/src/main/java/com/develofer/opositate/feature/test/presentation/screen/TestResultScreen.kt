package com.develofer.opositate.feature.test.presentation.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.develofer.opositate.R
import com.develofer.opositate.feature.test.presentation.viewmodel.TestResultUiState
import com.develofer.opositate.feature.test.presentation.viewmodel.TestResultViewModel
import com.develofer.opositate.utils.Constants.EMPTY_TEXT
import com.develofer.opositate.utils.Constants.TWO_DECIMALS_FORMAT
import com.develofer.opositate.utils.Constants.TWO_DIGITS_FORMAT
import java.util.Locale

@Composable
fun TestResultScreen(
    testResultId: String,
    testResultViewModel: TestResultViewModel = hiltViewModel(),
    isDarkTheme: Boolean
) {
    val uiState by testResultViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isTestActive) {
        testResultViewModel.getTestResult(testResultId)
    }

    uiState.testResult?.let { testResult ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TestHeader(uiState.currentQuestionIndex, testResult.questions.size, testResult.completionTime)
            if (uiState.currentQuestionIndex > -1) {
                CurrentQuestionContent(uiState, isDarkTheme)
            } else {
                TestStatisticsContent(uiState, testResult.completionTime)
            }
            NavigationButtons(
                currentQuestionIndex = uiState.currentQuestionIndex,
                totalQuestions = testResult.questions.size,
                onPrevious = { testResultViewModel.changeQuestion(false) },
                onNext = { testResultViewModel.changeQuestion(true) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun TestHeader(currentQuestionIndex: Int, totalQuestions: Int, completionTime: Int?) {
    val showHeader = currentQuestionIndex > -1
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val questionText = if (showHeader) "${currentQuestionIndex + 1}/$totalQuestions" else EMPTY_TEXT
        Text(
            text = questionText,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        val timerText = if (showHeader) {
            stringResource(id = R.string.test_solving_screen__text__time,
                String.format(Locale.getDefault(), TWO_DIGITS_FORMAT, (completionTime ?: 0) / 60),
                String.format(Locale.getDefault(), TWO_DIGITS_FORMAT, (completionTime ?: 0) % 60)
            )
        } else EMPTY_TEXT
        Text(
            text = timerText,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun CurrentQuestionContent(uiState: TestResultUiState, isDarkTheme: Boolean) {
    uiState.testResult?.questions?.get(uiState.currentQuestionIndex)?.let { currentQuestion ->
        Text(
            text = currentQuestion.question,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            textAlign = TextAlign.Center
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(items = currentQuestion.options) { index, option ->
                val borderColor by animateColorAsState(
                    targetValue = when {
                        (currentQuestion.correctAnswer == index) && (currentQuestion.selectedAnswer == index) && isDarkTheme -> MaterialTheme.colorScheme.primary
                        (currentQuestion.correctAnswer == index) && (currentQuestion.selectedAnswer == index) && !isDarkTheme -> MaterialTheme.colorScheme.secondary
                        (currentQuestion.correctAnswer == index) && (currentQuestion.selectedAnswer != index) -> MaterialTheme.colorScheme.secondary
                        (currentQuestion.correctAnswer != index) && (currentQuestion.selectedAnswer == index) -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.outlineVariant
                    }, label = ""
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(2.dp, borderColor)
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
    }
}

@Composable
fun TestStatisticsContent(uiState: TestResultUiState, completionTime: Int) {
    val numCorrect = uiState.testResult?.questions?.count { it.selectedAnswer == it.correctAnswer }
    val numIncorrect = uiState.testResult?.questions?.count { it.selectedAnswer != it.correctAnswer && it.selectedAnswer != null }
    val numUnanswered = uiState.testResult?.questions?.count { it.selectedAnswer == null }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(uiState.testResult?.name ?: "", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Text("Questions Correct: $numCorrect/${uiState.testResult?.questions?.size}", color = Color.Green)
        Text("Questions Incorrect: $numIncorrect/${uiState.testResult?.questions?.size}", color = Color.Red)
        Text("Questions Unanswered: $numUnanswered/${uiState.testResult?.questions?.size}", color = Color.Gray)
        Text(String.format(Locale.getDefault(), TWO_DECIMALS_FORMAT, uiState.testResult?.score ?: 0.0f),
            fontWeight = FontWeight.Bold, fontSize = 32.sp)
        Text("Time Taken: " +
            stringResource(
                id = R.string.test_solving_screen__text__time,
                String.format(Locale.getDefault(), TWO_DIGITS_FORMAT, (completionTime) / 60),
                String.format(Locale.getDefault(), TWO_DIGITS_FORMAT, (completionTime) % 60)
            )
        )
    }
}

@Composable
fun NavigationButtons(
    modifier: Modifier = Modifier,
    currentQuestionIndex: Int,
    totalQuestions: Int,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    Spacer(modifier = modifier)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (currentQuestionIndex > -1) {
            IconButton(onClick = onPrevious) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.test_solving_screen__content_description_before_button))
            }
        } else {
            Spacer(modifier = Modifier.width(48.dp))
        }
        if (currentQuestionIndex < totalQuestions - 1) {
            IconButton(onClick = onNext) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, stringResource(R.string.test_solving_screen__content_description__after_button))
            }
        } else {
            Spacer(modifier = Modifier.width(48.dp))
        }
    }
}
