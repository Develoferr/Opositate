package com.develofer.opositate.feature.test.domain.usecase

import com.develofer.opositate.feature.profile.data.model.PsTest
import com.develofer.opositate.feature.test.domain.repository.TestRepository
import com.develofer.opositate.main.data.model.Result
import javax.inject.Inject

class GetTestUseCase @Inject constructor(
    private val testRepository: TestRepository
) {
    suspend operator fun invoke(testId: String): Result<PsTest?> =
        testRepository.getTest(testId)
}