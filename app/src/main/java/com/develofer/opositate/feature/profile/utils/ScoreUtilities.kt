package com.develofer.opositate.feature.profile.utils

import com.develofer.opositate.feature.profile.domain.model.ScoreAbility
import com.develofer.opositate.feature.profile.domain.model.ScoreTask
import com.develofer.opositate.feature.profile.domain.model.UserScoresByGroup
import com.develofer.opositate.feature.profile.domain.usecase.GetAbilityGroupIdUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetGroupAbilityResIdUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetGroupResIdIconUseCase

fun List<ScoreAbility>.toUserScoresByGroupList(
    getGroupAbilityResIdUseCase: GetGroupAbilityResIdUseCase,
    getAbilityGroupIdUseCase: GetAbilityGroupIdUseCase,
    getGroupIdIcon: GetGroupResIdIconUseCase
): List<UserScoresByGroup> {

    val groupedScores = mutableMapOf<Int, MutableList<ScoreAbility>>()
    this.forEachIndexed { index, score ->
        val scoreGroupId = getAbilityGroupIdUseCase(index)
        groupedScores.computeIfAbsent(scoreGroupId) { mutableListOf() }.add(score)
    }

    return groupedScores.map { (groupId, scores) ->
        UserScoresByGroup(
            userScoresGroupNameResId = getGroupAbilityResIdUseCase(groupId),
            userScoresGroupIconResId = getGroupIdIcon(groupId),
            scoresByGroup = scores.map { ability ->
                ScoreAbility(
                    abilityNameResId = ability.abilityNameResId,
                    startScore = ability.startScore,
                    presentScore = ability.presentScore,
                    taskScores = ability.taskScores.map { task ->
                        ScoreTask(
                            taskNameResId = task.taskNameResId,
                            startScore = task.startScore,
                            presentScore = task.presentScore
                        )
                    },
                    expanded = ability.expanded
                )
            }
        )
    }
}