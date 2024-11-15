package com.develofer.opositate.feature.profile.presentation.model

import com.develofer.opositate.feature.profile.data.model.PsTest
import com.develofer.opositate.feature.profile.data.model.Question

data class PsTestDocumentResponse(
    val tests: List<PsTestResponse> = emptyList()
)

data class PsTestResponse(
    val id: Int = 0,
    val abilityId: Int = 0,
    val abilityTaskId: Int = 0,
    val questions: List<QuestionResponse> = emptyList(),
    var isEnabled: Boolean = false
)

class QuestionResponse (
    val id: Int = 0,
    val question: String = "",
    val options: List<String> = emptyList(),
    val correctAnswer: Int = 0
)

fun PsTestResponse.toVO() = PsTest(
    id = id,
    abilityId = abilityId,
    abilityTaskId = abilityTaskId,
    questions = questions.map { it.toVO() },
    isEnabled = isEnabled
)

fun QuestionResponse.toVO() = Question(
    id = id,
    question = question,
    options = options,
    correctAnswer = correctAnswer
)