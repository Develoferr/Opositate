package com.develofer.opositate.feature.profile.data.model

data class UserScores(
    val level: Int = 0,
    val scores: List<Score> = emptyList(),
)

data class Score(
    val abilityName: String = "",
    val startScore: Int = 0,
    val presentScore: Int = 0
)