package com.develofer.opositate.feature.test

import com.develofer.opositate.feature.profile.PsTest
import javax.inject.Inject

class GetTestUseCase @Inject constructor(
    private val testRepository: TestRepository
) {
    suspend operator fun invoke(testId: String): PsTest? {
        return testRepository.getTest(testId)
    }
}