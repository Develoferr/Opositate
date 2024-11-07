package com.develofer.opositate.feature.test.utils

import com.develofer.opositate.feature.profile.data.model.PsTest
import com.develofer.opositate.feature.profile.data.model.Question
import com.develofer.opositate.feature.profile.data.model.QuestionResult
import com.develofer.opositate.feature.profile.data.model.TestResult
import com.develofer.opositate.feature.profile.presentation.model.PsTestVO
import com.develofer.opositate.feature.test.data.model.TestItem
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



fun PsTestVO.toTestItem(): TestItem {
    return TestItem(number = this.number, title = this.name, isEnabled = this.isEnabled)
}