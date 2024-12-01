package com.develofer.opositate.feature.profile.data.model

import com.develofer.opositate.main.data.provider.TestType
import com.develofer.opositate.utils.StringConstants.EMPTY_STRING
import com.google.firebase.Timestamp

data class TestResultDocument(
    val solvedTests: List<TestResult> = emptyList()
)

data class TestResult(
    val id: String = EMPTY_STRING,
    val testType: TestType = TestType.GENERAL,
    val testId: Int = 0,
    val abilityId: Int = 0,
    val taskId: Int = 0,
    val questions : List<QuestionResult> = emptyList(),
    val maxTime : Int = 0,
    val completionTime : Int = 0,
    val timestamp: Timestamp? = null,
    val score: Float = 0f
)

data class QuestionResult(
    val id: Int = 0,
    val correctAnswer: Int = 0,
    val selectedAnswer: Int? = null,
)