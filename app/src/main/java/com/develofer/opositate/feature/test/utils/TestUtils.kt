package com.develofer.opositate.feature.test.utils

import com.develofer.opositate.feature.profile.data.model.PsTest
import com.develofer.opositate.feature.profile.data.model.Question
import com.develofer.opositate.feature.profile.data.model.QuestionResult
import com.develofer.opositate.feature.profile.data.model.TestResult
import com.develofer.opositate.feature.profile.domain.usecase.GetAbilityGroupIdUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetAbilityResIdUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetGroupAbilityResIdUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetGroupResIdIconUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetTaskStringResIdUseCase
import com.develofer.opositate.feature.test.domain.model.AbilityAsksItem
import com.develofer.opositate.feature.test.domain.model.CompleteTestAsksList
import com.develofer.opositate.feature.test.domain.model.TaskAsksItem
import com.develofer.opositate.feature.test.domain.model.TestAskItem
import com.develofer.opositate.feature.test.domain.model.TestAsksByGroup
import com.develofer.opositate.main.data.provider.ResourceProvider
import com.google.firebase.Timestamp
import java.util.UUID

fun PsTest.correctTest(completionTime: Int, timestamp: Timestamp): TestResult =
    TestResult(
        id = UUID.randomUUID().toString(),
        testId = this.id,
        questions = this.questions.map { question ->
            QuestionResult(
                id = question.id,
                correctAnswer = question.correctAnswer,
                selectedAnswer = question.selectedAnswer,
            )
        },
        maxTime = this.maxTime,
        completionTime = completionTime,
        score = calculateScore(this.questions),
        timestamp = timestamp
    )

fun calculateScore(questions: List<Question>): Float {
    var correctAnswers = 0f
    var incorrectAnswers = 0f
    questions.forEach { question ->
        if (question.selectedAnswer == question.correctAnswer) correctAnswers += 1
        else if (question.selectedAnswer != null) incorrectAnswers += 1
    }
    return (correctAnswers - (incorrectAnswers / 3)) / questions.size
}

fun List<AbilityAsksItem>.toTestAsksByGroup(
    getAbilityGroupIdUseCase: GetAbilityGroupIdUseCase,
    getGroupAbilityResIdUseCase: GetGroupAbilityResIdUseCase,
    getGroupResIdIconUseCase: GetGroupResIdIconUseCase,
    getAbilityResIdUseCase: GetAbilityResIdUseCase
): List<TestAsksByGroup> {
    val groupedAsks = mutableMapOf<Int, MutableList<AbilityAsksItem>>()
    this.forEachIndexed { index, asksItem ->
        val scoreGroupId = getAbilityGroupIdUseCase(index)
        groupedAsks.computeIfAbsent(scoreGroupId) { mutableListOf() }.add(asksItem)
    }

    return groupedAsks.map { (groupId, asks) ->
        TestAsksByGroup(
            testAskGroupNameResId = getGroupAbilityResIdUseCase(groupId),
            testAskGroupIconResId = getGroupResIdIconUseCase(groupId),
            asksByGroup = asks.map { ask ->
                AbilityAsksItem(
                    abilityId = ask.abilityId,
                    approvedTests = ask.approvedTests,
                    failedTest = ask.failedTest,
                    tasksAsks = ask.tasksAsks.map { task ->
                        TaskAsksItem(
                            taskId = task.taskId,
                            approvedTests = task.approvedTests,
                            failedTest = task.failedTest,
                            testAsks = task.testAsks.map { testAsk ->
                                TestAskItem(
                                    testId = testAsk.testId,
                                    maxTime = testAsk.maxTime,
                                    correctAnswers = testAsk.correctAnswers,
                                    incorrectAnswers = testAsk.incorrectAnswers,
                                    completionTime = testAsk.completionTime,
                                    timestamp = testAsk.timestamp
                                )
                            },
                            taskName = task.taskName
                        )
                    },
                    abilityName = ask.abilityName
                )
            }
        )
    }
}

fun CompleteTestAsksList.addNames(
    testAsksList: CompleteTestAsksList,
    getAbilityResIdUseCase: GetAbilityResIdUseCase,
    getTaskStringResIdUseCase: GetTaskStringResIdUseCase,
    resourceProvider: ResourceProvider
) = CompleteTestAsksList(
    testByAbilityList = testAsksList.testByAbilityList.map {
        it.copy(
            abilityName = resourceProvider.getString(getAbilityResIdUseCase(it.abilityId)),
            tasksAsks = it.tasksAsks.map { taskAsks ->
                taskAsks.copy(
                    taskName = resourceProvider.getString(getTaskStringResIdUseCase(it.abilityId, taskAsks.taskId))
                )
            }
        )
    }
)