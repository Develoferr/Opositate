package com.develofer.opositate.feature.profile.presentation.model

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