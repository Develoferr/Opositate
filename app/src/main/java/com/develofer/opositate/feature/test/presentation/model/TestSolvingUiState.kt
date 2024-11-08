package com.develofer.opositate.feature.test.presentation.model

import com.develofer.opositate.feature.profile.data.model.PsTest

data class TestSolvingUiState(
    val currentQuestionIndex: Int = 0,
    val timeCount: Int = 0,
    val isTestActive: Boolean = false,
    val showStartDialog: Boolean = true,
    val showPauseDialog: Boolean = false,
    val test: PsTest? = null
)