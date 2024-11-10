package com.develofer.opositate.feature.profile.domain.usecase

import com.develofer.opositate.main.data.model.AbilityDataProvider
import javax.inject.Inject

class GetAbilityNameUseCase @Inject constructor(
    private val abilityDataProvider: AbilityDataProvider
) {
    operator fun invoke(abilityId: Int): String {
        return abilityDataProvider.getAbilityName(abilityId)
    }
}