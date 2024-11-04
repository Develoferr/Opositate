package com.develofer.opositate.feature.profile

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