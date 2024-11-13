package com.develofer.opositate.feature.profile.domain.usecase

import com.develofer.opositate.main.data.provider.AbilityDataProvider
import javax.inject.Inject

class GetAbilityNameUseCase @Inject constructor(
    private val abilityDataProvider: AbilityDataProvider
) {
    operator fun invoke(abilityId: Int): Int =
        abilityDataProvider.getAbilityName(abilityId)
}