package com.develofer.opositate.feature.test.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.feature.profile.data.model.SolvedTest
import com.develofer.opositate.feature.test.domain.usecase.GetTestResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestResultViewModel @Inject constructor(
    private val getTestResultUseCase: GetTestResultUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TestResultUiState())
    val uiState: StateFlow<TestResultUiState> = _uiState.asStateFlow()

    fun changeQuestion(sum: Boolean) {
        if (sum) _uiState.update { it.copy(currentQuestionIndex = it.currentQuestionIndex + 1) }
        else _uiState.update { it.copy(currentQuestionIndex = it.currentQuestionIndex - 1) }
    }
    fun getTestResult(testResultId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(testResult = getTestResultUseCase(testResultId)) }
        }
    }
}

data class TestResultUiState(
    val currentQuestionIndex: Int = -1,
    val timeCount: Int = 0,
    val isTestActive: Boolean = false,
    val showStartDialog: Boolean = true,
    val showPauseDialog: Boolean = false,
    val testResult: SolvedTest? = null
)