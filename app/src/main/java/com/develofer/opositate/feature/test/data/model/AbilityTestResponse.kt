package com.develofer.opositate.feature.test.data.model

import com.develofer.opositate.utils.StringConstants.EMPTY_STRING

data class AbilityTestsResponse(
    val abilityId: Int = 0,
    val tasks: List<TaskTestsResponse> = emptyList()
)

data class TaskTestsResponse(
    val taskId: Int = 0,
    val difficultyLevels: List<DifficultyLevelTestsResponse> = emptyList()
)

data class DifficultyLevelTestsResponse(
    val difficultyId: Int = 0,
    val tests: List<TestByAbilityResponse> = emptyList()
)

data class TestByAbilityResponse(
    val testId: Int = 0,
    val questions: List<QuestionResponse> = emptyList()
)

data class QuestionResponse(
    val id: Int = 0,
    val question: String = EMPTY_STRING,
    val options: List<String> = emptyList(),
    val correctAnswer: Int = 0,
)