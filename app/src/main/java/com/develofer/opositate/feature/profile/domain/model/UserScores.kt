package com.develofer.opositate.feature.profile.domain.model

data class UserScores(
    val level: Int = 0,
    val scores: List<ScoreAbility> = emptyList(),
)

data class ScoreAbility(
    val abilityNameResId: Int = 0,
    val startScore: Int = 0,
    val presentScore: Int = 0,
    val taskScores: List<ScoreTask> = emptyList(),
    var expanded: Boolean = false
)

data class ScoreTask (
    val taskNameResId: Int = 0,
    val startScore: Int = 0,
    val presentScore: Int = 0
)

data class UserScoresByGroup(
    val userScoresGroupResId: Int = 0,
    val scoresByGroup: List<ScoreAbility> = emptyList()
)
