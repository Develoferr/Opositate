package com.develofer.opositate.feature.test.domain.usecase

import com.develofer.opositate.feature.profile.presentation.model.PsTestVO
import com.develofer.opositate.feature.test.domain.repository.TestRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTestListUseCase @Inject constructor(
    private val testRepository: TestRepository
) {
    suspend operator fun invoke(): List<PsTestVO> {
        return testRepository.getTestList()
    }
}