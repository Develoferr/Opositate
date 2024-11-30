package com.develofer.opositate.feature.test.presentation.model

import com.develofer.opositate.main.data.provider.TestType
import com.develofer.opositate.utils.StringConstants.EMPTY_STRING

data class PsTest(
    val abilityId: Int? = null,
    val difficultyLevel: Int? = null,
    val testId: Int? = null,
    val testType: TestType,
    val testName: String = EMPTY_STRING,
    val questions: List<Question>, val maxTime: Int = 0,
    var isEnabled: Boolean = false
)

data class Question(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswer: Int,
    var selectedAnswer: Int? = null
)