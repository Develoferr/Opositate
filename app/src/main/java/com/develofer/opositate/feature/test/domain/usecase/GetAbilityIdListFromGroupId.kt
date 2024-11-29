package com.develofer.opositate.feature.test.domain.usecase

import com.develofer.opositate.main.data.provider.TestDataProvider
import javax.inject.Inject

class GetAbilityIdListFromGroupId @Inject constructor(
    private val testDataProvider: TestDataProvider
) {
    operator fun invoke(groupId: Int): List<Int> {
        return testDataProvider.getAbilityIdListFromGroupId(groupId)
    }
}