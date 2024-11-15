package com.develofer.opositate.feature.test.domain.model

import com.develofer.opositate.utils.StringConstants.EMPTY_STRING
import com.google.firebase.Timestamp

data class CompleteTestAsksList(
    val testByAbilityList: List<AbilityAsksItem>
)

data class AbilityAsksItem(
    val abilityId: Int,
    val approvedTests: Int,
    val failedTest: Int,
    val tasksAsks: List<TaskAsksItem>,
    val abilityName: String = EMPTY_STRING
)

data class TaskAsksItem(
    val taskId: Int,
    val approvedTests: Int,
    val failedTest: Int,
    val testAsks: List<TestAskItem>,
    val taskName: String = EMPTY_STRING
)

data class TestAskItem(
    val testId: String,
    val maxTime: Int,
    val correctAnswers: Int,
    val incorrectAnswers: Int,
    val completionTime: Int,
    val timestamp: Timestamp? = null,
)
