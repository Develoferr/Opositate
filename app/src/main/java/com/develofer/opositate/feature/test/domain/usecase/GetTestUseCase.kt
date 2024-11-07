package com.develofer.opositate.feature.test.domain.usecase

import com.develofer.opositate.feature.profile.data.model.PsTest
import com.develofer.opositate.feature.test.domain.repository.TestRepository
import javax.inject.Inject

class GetTestUseCase @Inject constructor(
    private val testRepository: TestRepository
) {
    suspend operator fun invoke(testId: String): PsTest? {
        return testRepository.getTest(testId)
    }
}