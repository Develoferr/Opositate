package com.develofer.opositate.feature.profile.domain.model

data class UserScoresVO(
    val level: Int = 0,
    val scores: List<ScoreAbilityVO> = emptyList(),
)

data class ScoreAbilityVO(
    val abilityNameResId: Int = 0,
    val startScore: Int = 0,
    val presentScore: Int = 0,
    val taskScores: List<ScoreTaskVO> = emptyList(),
    var expanded: Boolean = false
)

data class ScoreTaskVO (
    val taskNameResId: Int = 0,
    val startScore: Int = 0,
    val presentScore: Int = 0
)
