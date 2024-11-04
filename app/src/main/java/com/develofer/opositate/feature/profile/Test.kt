package com.develofer.opositate.feature.profile

import com.google.firebase.Timestamp

data class Test(
    val id: Int,
    val name: String,
    val questions: List<Question>,
    val maxTime: Int = 0,
)

data class Question(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswer: Int,
    var selectedAnswer: Int? = null
)

data class SolvedTest(
    val id: Timestamp,
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
    val correctAnswer: Int,
    val selectedAnswer: Int?,
    val result: QuestionResult
)

enum class QuestionResult {
    CORRECT, INCORRECT, UNANSWERED
}

fun Test.correctTest(completionTime: Int, timestamp: Timestamp): SolvedTest =
    SolvedTest(
        id = timestamp,
        testId = this.id,
        name = this.name,
        questions = this.questions.map { question ->
            SolvedQuestion(
                id = question.id,
                question = question.question,
                options = question.options,
                correctAnswer = question.correctAnswer,
                selectedAnswer = question.selectedAnswer,
                result = solveQuestion(
                    correctAnswer = question.correctAnswer,
                    selectedAnswer = question.selectedAnswer
                )
            )
        },
        maxTime = this.maxTime,
        completionTime = completionTime,
        score = calculateScore(this.questions)
    )

fun solveQuestion(correctAnswer: Int, selectedAnswer: Int?): QuestionResult {
    return when (selectedAnswer) {
        null -> QuestionResult.UNANSWERED
        correctAnswer -> QuestionResult.CORRECT
        else -> QuestionResult.INCORRECT
    }
}

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
