package com.develofer.opositate.feature.test.domain.usecase

import com.develofer.opositate.feature.profile.presentation.model.PsTestDocumentResponse
import com.develofer.opositate.feature.test.domain.repository.TestRepository
import com.develofer.opositate.main.data.model.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTestListByAbilityUseCase @Inject constructor(
    private val testRepository: TestRepository
) {
    suspend operator fun invoke(abilityId: Int): Result<List<PsTestDocumentResponse>> =
        testRepository.getTestListByAbility(abilityId)
}