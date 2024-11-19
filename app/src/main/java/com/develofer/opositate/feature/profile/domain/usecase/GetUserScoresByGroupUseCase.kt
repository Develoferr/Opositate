package com.develofer.opositate.feature.profile.domain.usecase

import com.develofer.opositate.feature.profile.domain.model.UserScoresByGroup
import com.develofer.opositate.feature.profile.presentation.viewmodel.toUserScoresByGroupList
import com.develofer.opositate.main.data.model.Result
import javax.inject.Inject


class GetUserScoresByGroupUseCase @Inject constructor(
    private val getUserScores: GetUserScoresUseCase,
    private val getGroupId: GetAbilityGroupIdUseCase,
    private val getGroupAbility: GetGroupAbilityResIdUseCase
) {
    suspend operator fun invoke(): Result<List<UserScoresByGroup>> =
        when (val result = getUserScores()) {
            is Result.Success -> {
                val scoreAbilitiesByGroup = result.data.scores.toUserScoresByGroupList(getGroupAbility, getGroupId)
                Result.Success(scoreAbilitiesByGroup)
            }
            is Result.Error -> Result.Error(result.exception)
            is Result.Loading -> Result.Loading
        }
}