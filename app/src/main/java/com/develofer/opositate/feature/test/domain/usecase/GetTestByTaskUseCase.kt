package com.develofer.opositate.feature.test.domain.usecase

import com.develofer.opositate.feature.test.domain.repository.TestRepository
import com.develofer.opositate.feature.test.presentation.model.PsTest
import com.develofer.opositate.main.data.model.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTestByTaskUseCase @Inject constructor(
    private val testRepository: TestRepository
) {
    suspend operator fun invoke(abilityId: Int, taskId: Int, difficultyId: Int, testId: Int?): Result<PsTest?> =
        testRepository.getTestByTask(abilityId, taskId, testId ?: 0, difficultyId)
}
