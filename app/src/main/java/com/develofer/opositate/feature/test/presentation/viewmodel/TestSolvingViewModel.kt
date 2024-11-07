package com.develofer.opositate.feature.test.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.feature.profile.data.model.PsTest
import com.develofer.opositate.feature.profile.data.model.TestResult
import com.develofer.opositate.feature.test.domain.usecase.GetTestUseCase
import com.develofer.opositate.feature.test.domain.usecase.SaveTestResultUseCase
import com.develofer.opositate.feature.test.utils.correctTest
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestSolvingViewModel @Inject constructor(
    private val getTestUseCase: GetTestUseCase,
    private val saveTestResultUseCase: SaveTestResultUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TestSolvingUiState())
    val uiState: StateFlow<TestSolvingUiState> = _uiState.asStateFlow()

    fun updateQuestionIndex(sum: Boolean) {
        if (sum) _uiState.update { it.copy(currentQuestionIndex = it.currentQuestionIndex + 1) }
        else _uiState.update { it.copy(currentQuestionIndex = it.currentQuestionIndex - 1) }
    }

    fun toggleShowStartDialogVisibility(show: Boolean) {
        _uiState.update { it.copy(showStartDialog = show) }
    }

    fun toggleShowPauseDialogVisibility(show: Boolean) {
        _uiState.update { it.copy(showPauseDialog = show) }
    }

    fun toggleTestActive(isActive: Boolean) {
        _uiState.update { it.copy(isTestActive = isActive) }
    }

    fun incrementTime() {
        _uiState.update { it.copy(timeCount = it.timeCount + 1) }
    }

    fun updateSelectedAnswer(index: Int) {
        _uiState.update {
            it.copy(
                test = it.test?.copy(
                    questions = it.test.questions.mapIndexed { i, question ->
                        if (i == question.id) question.copy(selectedAnswer = index) else question
                    }
                )
            )
        }
    }

    fun getTest(testId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(test = getTestUseCase(testId)) }
        }
    }

    fun correctTest(navigateToTestResult: (testResultId: String) -> Unit) {
        val testResult = _uiState.value.test?.correctTest(_uiState.value.timeCount, Timestamp.now())
        testResult?.let { saveTestResult(it) }
        navigateToTestResult(testResult?.id ?: "")
    }

    private fun saveTestResult(testResult: TestResult) {
        viewModelScope.launch {
            saveTestResultUseCase(testResult)
        }
    }
}

data class TestSolvingUiState(
    val currentQuestionIndex: Int = 0,
    val timeCount: Int = 0,
    val isTestActive: Boolean = false,
    val showStartDialog: Boolean = true,
    val showPauseDialog: Boolean = false,
    val test: PsTest? = null
)