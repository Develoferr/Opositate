package com.develofer.opositate.feature.test.domain.usecase

import com.develofer.opositate.feature.profile.data.model.TestResult
import com.develofer.opositate.feature.profile.domain.usecase.GetUserIdUseCase
import com.develofer.opositate.feature.test.domain.repository.SolvedTestRepository
import com.develofer.opositate.main.data.model.Result
import javax.inject.Inject

class SaveTestResultUseCase @Inject constructor(
    private val solvedTestRepository: SolvedTestRepository,
    private val getUserIdUseCase: GetUserIdUseCase
) {
    suspend operator fun invoke(testResult: TestResult): Result<Unit> {
        return when (val userIdResult = getUserIdUseCase()) {
            is Result.Success -> {
                solvedTestRepository.saveTestResult(testResult, userIdResult.data, true)
                Result.Success(Unit)
            }
            is Result.Error -> userIdResult
            Result.Loading -> Result.Loading
        }
    }
}