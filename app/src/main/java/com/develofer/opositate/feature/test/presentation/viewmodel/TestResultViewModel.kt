package com.develofer.opositate.feature.test.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.feature.test.domain.usecase.GetTestResultUseCase
import com.develofer.opositate.feature.test.presentation.model.TestResultUiState
import com.develofer.opositate.main.data.model.Result
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
    fun getTestResult(
        testResultId: Int,
        testTypeId: Int,
        difficultId: Int?,
        groupId: Int?,
        abilityId: Int?,
        taskId: Int?
    ) {
        viewModelScope.launch {
            when (val result = getTestResultUseCase(testResultId, testTypeId, difficultId, groupId, abilityId, taskId)) {
                is Result.Success -> {
                    _uiState.update { it.copy(testResult = result.data) }
                }
                is Result.Error -> {
                    // Handle error with dialog
                }
                is Result.Loading -> { }
            }
        }
    }
}