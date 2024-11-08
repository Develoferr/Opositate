package com.develofer.opositate.feature.test.presentation.model

import com.develofer.opositate.feature.profile.data.model.SolvedTest

data class TestResultUiState(
    val currentQuestionIndex: Int = -1,
    val timeCount: Int = 0,
    val isTestActive: Boolean = false,
    val showStartDialog: Boolean = true,
    val showPauseDialog: Boolean = false,
    val testResult: SolvedTest? = null
)