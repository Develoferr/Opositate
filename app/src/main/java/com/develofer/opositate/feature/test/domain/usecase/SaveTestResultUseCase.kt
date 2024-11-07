package com.develofer.opositate.feature.test.domain.usecase

import com.develofer.opositate.feature.profile.domain.usecase.GetUserIdUseCase
import com.develofer.opositate.feature.profile.data.model.TestResult
import com.develofer.opositate.feature.test.domain.repository.SolvedTestRepository
import javax.inject.Inject

class SaveTestResultUseCase @Inject constructor(
    private val solvedTestRepository: SolvedTestRepository,
    private val getUserIdUseCase: GetUserIdUseCase
) {
    suspend operator fun invoke(testResult: TestResult) {
        val userId = getUserIdUseCase()
        if (userId.isNotBlank()) {
            solvedTestRepository.saveTestResult(testResult, userId, true )
        }
    }
}