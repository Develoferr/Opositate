package com.develofer.opositate.feature.test.data.model

import com.develofer.opositate.feature.test.data.model.TaskAsksMapper.toDomain
import com.develofer.opositate.feature.test.data.model.TestAskMapper.toDomain
import com.develofer.opositate.feature.test.domain.model.AbilityAsksItem
import com.develofer.opositate.feature.test.domain.model.CompleteTestAsksList
import com.develofer.opositate.feature.test.domain.model.TaskAsksItem
import com.develofer.opositate.feature.test.domain.model.TestAskItem
import com.google.firebase.Timestamp

data class CompleteTestAsksResult(
    val testAsks: List<AbilityAsksResult> = emptyList()
)

data class AbilityAsksResult(
    val abilityId: Int = 0,
    val approvedTests: Int = 0,
    val failedTest: Int = 0,
    val taskAsks: List<TaskAsksResult> = emptyList()
)

data class TaskAsksResult(
    val taskId: Int = 0,
    val approvedTests: Int = 0,
    val failedTest: Int = 0,
    val testAsks: List<TestAskResult> = emptyList()
)

data class TestAskResult(
    val testId: String = "",
    val maxTime: Int = 0,
    val correctAnswers: Int = 0,
    val incorrectAnswers: Int = 0,
    val completionTime: Int = 0,
    val timestamp: Timestamp? = null,
)

fun CompleteTestAsksResult.toDomain(): CompleteTestAsksList = CompleteTestAsksList(
    this.testAsks.map { taskAsks ->
        AbilityAsksItem(taskAsks.abilityId, taskAsks.approvedTests, taskAsks.failedTest, taskAsks.taskAsks.toDomain())
    }
)

object TaskAsksMapper {
    fun List<TaskAsksResult>.toDomain(): List<TaskAsksItem> = this.map { taskAsks ->
        TaskAsksItem(taskAsks.taskId, taskAsks.approvedTests, taskAsks.failedTest, taskAsks.testAsks.toDomain())
    }
}

object TestAskMapper {
    fun List<TestAskResult>.toDomain(): List<TestAskItem> = this.map { testAsk ->
        TestAskItem(testAsk.testId, testAsk.maxTime, testAsk.correctAnswers, testAsk.incorrectAnswers, testAsk.completionTime, testAsk.timestamp)
    }
}