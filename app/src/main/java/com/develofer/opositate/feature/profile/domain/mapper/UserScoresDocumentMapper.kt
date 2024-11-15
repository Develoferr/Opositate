package com.develofer.opositate.feature.profile.domain.mapper

import com.develofer.opositate.feature.profile.data.model.UserScoresResponse
import com.develofer.opositate.feature.profile.domain.model.ScoreAbility
import com.develofer.opositate.feature.profile.domain.model.ScoreTask
import com.develofer.opositate.feature.profile.domain.model.UserScores
import com.develofer.opositate.feature.profile.domain.usecase.GetAbilityResIdUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetTaskStringResIdUseCase

fun UserScoresResponse.toDomain(
    getAbilityStringIdUseCase: GetAbilityResIdUseCase,
    getTaskStringResIdUseCase: GetTaskStringResIdUseCase
): UserScores =
    UserScores(
        level = this.level,
        scores = this.scores.map { scoreResponse ->
            ScoreAbility(
                abilityNameResId = getAbilityStringIdUseCase(scoreResponse.abilityId),
                startScore = scoreResponse.startScore,
                presentScore = scoreResponse.presentScore,
                taskScores = scoreResponse.taskScores.map { taskScoreResponse ->
                    ScoreTask(
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