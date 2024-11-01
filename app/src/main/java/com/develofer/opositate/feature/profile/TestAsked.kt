package com.develofer.opositate.feature.profile

data class TestAsked(
    val questions: Map<String, QuestionResponse> = emptyMap()
)

data class QuestionResponse(
    val question: String = "",
    val answers: List<String> = emptyList(),
    val correctAnswerId: String = "",
    val userAnswerId: String = ""
)