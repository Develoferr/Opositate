package com.develofer.opositate.feature.profile.domain.usecase

import com.develofer.opositate.main.data.provider.AbilityDataProvider
import javax.inject.Inject

class GetTaskStringResIdUseCase @Inject constructor(
    private val abilityDataProvider: AbilityDataProvider
) {
    operator fun invoke(abilityId: Int, abilityTaskId: Int): Int =
        abilityDataProvider.getTaskStringResId(abilityId, abilityTaskId)
}