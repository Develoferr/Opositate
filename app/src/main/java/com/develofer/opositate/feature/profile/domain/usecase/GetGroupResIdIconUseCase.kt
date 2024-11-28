package com.develofer.opositate.feature.profile.domain.usecase

import com.develofer.opositate.main.data.provider.TestDataProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetGroupResIdIconUseCase @Inject constructor(
    private val testDataProvider: TestDataProvider
) {
    operator fun invoke(groupId: Int): Int =
        testDataProvider.getGroupResIdIcon(groupId)
}