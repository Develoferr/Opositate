package com.develofer.opositate.feature.profile

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Parcelize
@Serializable
data class PsTest(
    val id: Int,
    val name: String,
    val questions: List<Question>,
    val maxTime: Int = 0,
): Parcelable

@Parcelize
@Serializable
data class Question(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswer: Int,
    var selectedAnswer: Int? = null
): Parcelable

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

fun PsTest.correctTest(completionTime: Int, timestamp: Timestamp): SolvedTest =
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

val psTestNavType = object : NavType<PsTest>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): PsTest? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, PsTest::class.java)
        } else {
            bundle.getParcelable(key)
        }

    override fun parseValue(value: String): PsTest =
        Json.decodeFromString<PsTest>(value)

    override fun put(bundle: Bundle, key: String, value: PsTest) {
        bundle.putParcelable(key, value)
    }

    override fun serializeAsValue(value: PsTest): String =
        Uri.encode(Json.encodeToString(value))
}