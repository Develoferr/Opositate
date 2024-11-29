package com.develofer.opositate.feature.test.domain.usecase

import com.develofer.opositate.main.data.provider.TestDataProvider
import com.develofer.opositate.main.data.provider.TestType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTestTypeByIdUseCase @Inject constructor(
    private val testDataProvider: TestDataProvider
) {
    operator fun invoke(testTypeId: Int): TestType =
        testDataProvider.getTestTypeById(testTypeId)
}