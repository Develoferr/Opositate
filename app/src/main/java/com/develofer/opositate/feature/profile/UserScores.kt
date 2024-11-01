package com.develofer.opositate.feature.profile

data class UserScores(
    val level: Int = 0,
    val scores: List<Score> = emptyList(),
)

data class Score(
    val ability: String = "",
    val startScore: Int = 0,
    val presentScore: Int = 0
)