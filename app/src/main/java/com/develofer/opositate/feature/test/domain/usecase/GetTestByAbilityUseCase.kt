package com.develofer.opositate.feature.test.domain.usecase

import com.develofer.opositate.feature.test.domain.repository.TestRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTestByAbilityUseCase @Inject constructor(
    private val testRepository: TestRepository
) {
    suspend operator fun invoke(difficultId: Int, abilityId: Int, testId: Int?) =
        testRepository.getTestByAbility(abilityId, difficultId, testId ?: 0)
}
