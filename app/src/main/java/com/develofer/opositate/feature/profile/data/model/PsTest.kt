package com.develofer.opositate.feature.profile.data.model

import com.develofer.opositate.utils.StringConstants.EMPTY_STRING

data class PsTest(
    val id: Int = 0,
    val name: String = EMPTY_STRING,
    val questions: List<Question> = emptyList(),
    val maxTime: Int = 0,
    var isEnabled: Boolean = false
) {
    val number: Int
        get() = id + 1
}

data class Question(
    val id: Int = 0,
    val question: String = EMPTY_STRING,
    val options: List<String> = emptyList(),
    val correctAnswer: Int = 0,
    var selectedAnswer: Int? = null
)