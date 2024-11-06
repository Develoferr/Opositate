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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.develofer.opositate.R
import com.develofer.opositate.utils.Constants.TWO_DIGITS_FORMAT
import kotlinx.coroutines.delay
import java.util.Locale
import kotlin.time.Duration.Companion.seconds

@Composable
fun TestSolvingScreen(
    testId: String,
    testSolvingViewModel: TestSolvingViewModel = hiltViewModel()
) {
    val psTest by testSolvingViewModel.test.collectAsState()
    val testSize = psTest?.questions?.size
    val maxTime = psTest?.maxTime
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var showStartDialog by remember { mutableStateOf(true) }
    var showPauseDialog by remember { mutableStateOf(false) }
    var timeCount by remember { mutableIntStateOf(0) }
    var isTestActive by remember { mutableStateOf(false) }

    val testFinishModeCondition = if (maxTime == 0) true
                    else timeCount < (maxTime ?: 0)

    LaunchedEffect(isTestActive) {
        testSolvingViewModel.getTest(testId)
        if (isTestActive) {
            while (testFinishModeCondition) {
                delay(1.seconds)
                timeCount++
            }
            if (timeCount == maxTime) isTestActive = false
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

    if (showPauseDialog) {
        Dialog(onDismissRequest = { }) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "¿A ti también te cansa?",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 24.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "A nosotros también nos pasa, tomate un descanso y retómalo cuándo quieras.\n" +
                                "Si crees que vas a tardar mucho y no quieres perder tu progreso, te recomendamos que guardes tus avances.\n" +
                                "Podrás abrir la aplicación en cualquier momento y conservaremos tel estado del test.",
                        modifier = Modifier.padding(vertical = 24.dp),
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = {
//                                testSolvingViewModel.saveProgress()
//                                showPauseDialog = false
//                                isTestActive = false
                        }
                    ) {
                        Text("Guardar Progreso")
                    }
                    Button(
                        onClick = {
                            showPauseDialog = false
                            isTestActive = true
                        }
                    ) {
                        Text("Continuar")
                    }
                }
            }
        }
    }

    psTest?.let {
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
                if (maxTime != null) {
                    Text(
                        text = stringResource(id = R.string.test_solving_screen__text__time,
                            String.format(Locale.getDefault(), TWO_DIGITS_FORMAT, timeCount / 60),
                            String.format(Locale.getDefault(), TWO_DIGITS_FORMAT, timeCount % 60)
                        ),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (maxTime == 0) MaterialTheme.colorScheme.secondary
                        else
                            if (timeCount > (maxTime * 0.84)) Color.Red
                            else MaterialTheme.colorScheme.secondary
                    )
                }
                IconButton(onClick = {
                    isTestActive = false
                    showPauseDialog = true
                }) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_pause),
                        contentDescription = stringResource(R.string.test_solving_screen__content_description_before_button)
                    )
                }
            }

            val currentQuestion = psTest?.questions?.get(currentQuestionIndex)
            if (currentQuestion != null) {
                Text(
                    text = currentQuestion.question,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                currentQuestion?.options?.forEachIndexed { index, option ->
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

                if (testSize != null) {
                    if (currentQuestionIndex == testSize - 1) {
                        Button(
                            onClick = {
                                isTestActive = false
                            }
                        ) {
                            Text(stringResource(R.string.test_solving_screen__text_btn__finish).uppercase())
                        }
                    }
                }

                if (testSize != null) {
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
    }

}