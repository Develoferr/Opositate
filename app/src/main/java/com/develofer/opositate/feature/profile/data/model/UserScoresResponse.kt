package com.develofer.opositate.feature.profile.data.model

data class UserScoresResponse(
    val level: Int = 0,
    val scores: List<ScoreResponse> = emptyList(),
)

data class ScoreResponse(
    val abilityId: Int = 0,
    val startScore: Int = 0,
    val presentScore: Int = 0,
    val taskScores: List<TaskScoreResponse> = emptyList()
)

data class TaskScoreResponse(
    val taskId: Int = 0,
    val startScore: Int = 0,
    val presentScore: Int = 0,
)