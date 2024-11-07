package com.develofer.opositate.feature.test

import com.develofer.opositate.feature.profile.GetUserIdUseCase
import com.develofer.opositate.feature.profile.TestResult
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