package com.develofer.opositate.feature.test.utils

import com.develofer.opositate.feature.profile.data.model.QuestionResult
import com.develofer.opositate.feature.profile.data.model.TestResult
import com.develofer.opositate.feature.profile.domain.usecase.GetAbilityGroupIdUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetAbilityResIdUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetGroupAbilityResIdUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetGroupResIdIconUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetTaskStringResIdUseCase
import com.develofer.opositate.feature.test.data.model.AbilityTestsResponse
import com.develofer.opositate.feature.test.data.model.QuestionResponse
import com.develofer.opositate.feature.test.domain.model.AbilityAsksItem
import com.develofer.opositate.feature.test.domain.model.CompleteTestAsksList
import com.develofer.opositate.feature.test.domain.model.TaskAsksItem
import com.develofer.opositate.feature.test.domain.model.TestAskItem
import com.develofer.opositate.feature.test.domain.model.TestAsksByGroup
import com.develofer.opositate.feature.test.presentation.model.PsTest
import com.develofer.opositate.feature.test.presentation.model.Question
import com.develofer.opositate.main.data.provider.ResourceProvider
import com.develofer.opositate.main.data.provider.TestType
import com.google.firebase.Timestamp
import java.util.UUID

fun AbilityTestsResponse.toAbilityTest(difficultyLevel: Int, testId: Int): PsTest {
    val questionsPerTask = 4
    val startIndex = testId * questionsPerTask
    val endIndex = startIndex + questionsPerTask

    val selectedQuestions = tasks.flatMap { task ->
        task.difficultyLevels
            .find { it.difficultyId == difficultyLevel }
            ?.tests?.flatMap { it.questions }
            ?.slice(startIndex until endIndex)
            ?: emptyList()
    }

    return PsTest(
        abilityId = abilityId,
        difficultyLevel = difficultyLevel,
        testId = testId,
        questions = selectedQuestions.toQuestions(),
        maxTime = 0,
        isEnabled = true,
        testType = TestType.ABILITY
    )
}

fun List<AbilityTestsResponse>.toGroupTest(difficultyLevel: Int, testId: Int): PsTest {
    val questionsPerTask = 1
    val startIndex = testId * questionsPerTask

    val selectedQuestions = this.flatMap { abilityTestResponse ->
        abilityTestResponse.tasks
            .flatMap { task ->
                task.difficultyLevels
                    .firstOrNull { it.difficultyId == difficultyLevel }
                    ?.tests?.flatMap { it.questions }
                    ?.drop(startIndex)?.take(questionsPerTask)
                    ?: emptyList()
            }
    }

    return PsTest(
        difficultyLevel = difficultyLevel,
        questions = selectedQuestions.toQuestions(),
        maxTime = 0,
        isEnabled = true,
        testType = TestType.GROUP
    )
}


fun AbilityTestsResponse.toTaskTest(difficultyId: Int, abilityId: Int, taskId: Int, testId: Int): PsTest {
    val questionsNumber = 20
    val startIndex = testId * questionsNumber
    val endIndex = startIndex + (questionsNumber - 1)
    val questions = tasks.find { it.taskId == taskId }
        ?.difficultyLevels?.find { it.difficultyId == difficultyId }
        ?.tests?.flatMap { it.questions }
        ?.slice(startIndex..endIndex)
        ?: emptyList()

    return PsTest(
        abilityId = abilityId,
        difficultyLevel = difficultyId,
        testId = testId,
        questions = questions.toQuestions(),
        isEnabled = true,
        testType = TestType.TASK
    )
}

fun List<AbilityTestsResponse>.toGeneralTest(difficultyLevel: Int, testId: Int): PsTest {
    val questionsPerAbility = 2
    val allQuestions = this.flatMap { abilityTestResponse ->
        abilityTestResponse.tasks
            .flatMap { task ->
                task.difficultyLevels
                    .firstOrNull { it.difficultyId == difficultyLevel }
                    ?.tests
                    ?.flatMap { it.questions }
                    ?.map { task to it }
                    ?: emptyList()
            }
    }

    val questionsByTask = allQuestions
        .groupBy { it.first }
        .mapValues { entry -> entry.value.map { it.second } }
    val alternatedQuestions = mutableListOf<QuestionResponse>()
    val maxQuestions = questionsByTask.values.maxOf { it.size }
    for (i in 0 until maxQuestions) {
        questionsByTask.values.forEach { taskQuestions ->
            if (taskQuestions.size > i) {
                alternatedQuestions.add(taskQuestions[i])
            }
        }
    }
    val startIndex = testId * questionsPerAbility
    val selectedQuestions = alternatedQuestions
        .drop(startIndex)
        .take(questionsPerAbility)

    return PsTest(
        difficultyLevel = difficultyLevel,
        questions = selectedQuestions.toQuestions(),
        maxTime = 0,
        isEnabled = true,
        testType = TestType.GENERAL
    )
}


fun List<QuestionResponse>.toQuestions(): List<Question> =
    this.map { it.toQuestion() }

fun QuestionResponse.toQuestion(): Question =
    Question(
        id = id,
        question = question,
        options = options,
        correctAnswer = correctAnswer
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

fun PsTest.correctTest(
    completionTime: Int,
    timestamp: Timestamp
): TestResult =
    TestResult(
        id = UUID.randomUUID().toString(),
        testId = this.testId ?: 0,
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
        timestamp = timestamp,
        testType = this.testType
    )