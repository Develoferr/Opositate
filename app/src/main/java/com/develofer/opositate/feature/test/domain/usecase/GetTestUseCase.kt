package com.develofer.opositate.feature.test.domain.usecase

import com.develofer.opositate.feature.profile.presentation.model.PsTestResponse
import com.develofer.opositate.feature.test.domain.repository.TestRepository
import com.develofer.opositate.main.data.model.Result
import javax.inject.Inject

class GetTestUseCase @Inject constructor(
    private val testRepository: TestRepository
) {
    suspend operator fun invoke(testId: String, abilityId: Int, abilityTaskId: Int): Result<PsTestResponse?> =
        testRepository.getTest(testId, abilityId, abilityTaskId)
}