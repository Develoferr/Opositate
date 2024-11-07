package com.develofer.opositate.feature.profile.data.model

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