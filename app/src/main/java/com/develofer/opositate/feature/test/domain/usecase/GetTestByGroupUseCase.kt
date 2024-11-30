package com.develofer.opositate.feature.test.domain.usecase

import com.develofer.opositate.feature.test.domain.repository.TestRepository
import com.develofer.opositate.feature.test.presentation.model.PsTest
import com.develofer.opositate.main.data.model.Result
import javax.inject.Inject

class GetTestByGroupUseCase @Inject constructor(
    private val testRepository: TestRepository
) {
    suspend operator fun invoke(abilityIdList: List<Int>, difficultId: Int, testId: Int?): Result<PsTest?> =
        testRepository.getTestByGroup(abilityIdList, difficultId, testId ?: 0)
}
