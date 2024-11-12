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
        // Get User ID task
        val userIdResult = getUserIdUseCase()
        if (userIdResult is Result.Error) return Result.Error(userIdResult.exception)

        // Save Test Result task
        val userId = (userIdResult as Result.Success).data
        val saveResult = solvedTestRepository.saveTestResult(testResult, userId, true)
        return if (saveResult is Result.Error) Result.Error(saveResult.exception)
            else Result.Success(Unit)
    }
}