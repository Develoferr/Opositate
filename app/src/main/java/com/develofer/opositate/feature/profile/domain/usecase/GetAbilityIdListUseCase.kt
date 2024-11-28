package com.develofer.opositate.feature.profile.domain.usecase

import com.develofer.opositate.main.data.provider.TestDataProvider
import javax.inject.Inject

class GetAbilityIdListUseCase @Inject constructor(
    private val testDataProvider: TestDataProvider
) {
    operator fun invoke(): List<Map<String, Any>> {
        return testDataProvider.getAbilityIdList()
    }
}