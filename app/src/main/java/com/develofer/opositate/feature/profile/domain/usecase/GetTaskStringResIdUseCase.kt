package com.develofer.opositate.feature.profile.domain.usecase

import com.develofer.opositate.main.data.provider.TestDataProvider
import javax.inject.Inject

class GetTaskStringResIdUseCase @Inject constructor(
    private val testDataProvider: TestDataProvider
) {
    operator fun invoke(abilityId: Int, abilityTaskId: Int): Int =
        testDataProvider.getTaskStringResId(abilityId, abilityTaskId)
}