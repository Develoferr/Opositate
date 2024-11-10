package com.develofer.opositate.feature.profile.domain.model

data class UserScoresVO(
    val level: Int = 0,
    val scores: List<ScoreVO> = emptyList(),
)

data class ScoreVO(
    val abilityName: String = "",
    val startScore: Int = 0,
    val presentScore: Int = 0
)