package com.develofer.opositate.feature.test.domain.usecase

import com.develofer.opositate.feature.test.domain.repository.TestRepository
import com.develofer.opositate.feature.test.presentation.model.PsTest
import com.develofer.opositate.main.data.model.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTestGeneralUseCase @Inject constructor(
    private val testRepository: TestRepository
) {
    suspend operator fun invoke(difficultId: Int, testId: Int?): Result<PsTest?> =
        testRepository.getTestGeneral(difficultId, testId ?: 0)
}
