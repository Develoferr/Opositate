package com.develofer.opositate.feature.profile

import com.develofer.opositate.feature.test.TestItem
import com.google.firebase.Timestamp
import java.util.UUID

data class PsTest(
    val id: Int = 0,
    val name: String = "",
    val questions: List<Question> = emptyList(),
    val maxTime: Int = 0,
    var isEnabled: Boolean = false
) {
    val number: Int
        get() = id + 1
}

data class Question(
    val id: Int = 0,
    val question: String = "",
    val options: List<String> = emptyList(),
    val correctAnswer: Int = 0,
    var selectedAnswer: Int? = null
)

data class TestResultDocument(
    val solvedTests: List<TestResult> = emptyList()
)

data class TestResult(
    val id: String = "",
    val testId: Int = 0,
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

data class SolvedTest(
    val id: String,
    val testId: Int,
    val name: String,
    val questions : List<SolvedQuestion>,
    val maxTime : Int,
    val completionTime : Int,
    val score: Float
)

data class SolvedQuestion (
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswer: Int?,
    val selectedAnswer: Int?
)

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
        if (question.selectedAnswer == question.correctAnswer) {
            correctAnswers += 1
        } else if (question.selectedAnswer != null) {
            incorrectAnswers += 1
        }
    }
    return (correctAnswers - (incorrectAnswers / 3)) / questions.size
}

data class PsTestVO(
    val id: Int = 0,
    val name: String = "",
    var isEnabled: Boolean = false
) {
    val number: Int
        get() = id + 1
}

fun PsTestVO.toTestItem(): TestItem {
    return TestItem(number = this.number, title = this.name, isEnabled = this.isEnabled)
}