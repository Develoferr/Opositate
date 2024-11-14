package com.develofer.opositate.feature.profile.domain.mapper

import com.develofer.opositate.feature.profile.data.model.UserScoresResponse
import com.develofer.opositate.feature.profile.domain.model.ScoreAbilityVO
import com.develofer.opositate.feature.profile.domain.model.ScoreTaskVO
import com.develofer.opositate.feature.profile.domain.model.UserScoresVO
import com.develofer.opositate.feature.profile.domain.usecase.GetAbilityResIdUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetTaskStringResIdUseCase

fun UserScoresResponse.toVo(
    getAbilityStringIdUseCase: GetAbilityResIdUseCase,
    getTaskStringResIdUseCase: GetTaskStringResIdUseCase
): UserScoresVO =
    UserScoresVO(
        level = this.level,
        scores = this.scores.map { scoreResponse ->
            ScoreAbilityVO(
                abilityNameResId = getAbilityStringIdUseCase(scoreResponse.abilityId),
                startScore = scoreResponse.startScore,
                presentScore = scoreResponse.presentScore,
                taskScores = scoreResponse.tasks.map { taskScoreResponse ->
                    ScoreTaskVO(
                        taskNameResId = getTaskStringResIdUseCase(
                            scoreResponse.abilityId,
                            taskScoreResponse.taskId
                        ),
                        startScore = taskScoreResponse.startScore,
                        presentScore = taskScoreResponse.presentScore
                    )
                }
            )
        }
    )