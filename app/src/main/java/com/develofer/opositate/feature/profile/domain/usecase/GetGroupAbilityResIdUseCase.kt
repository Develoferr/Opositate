package com.develofer.opositate.feature.profile.domain.usecase

import com.develofer.opositate.main.data.provider.AbilityDataProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetGroupAbilityResIdUseCase @Inject constructor(
    private val abilityDataProvider: AbilityDataProvider
) {
    operator fun invoke(groupId: Int): Int =
        abilityDataProvider.getGroupResId(groupId)
}