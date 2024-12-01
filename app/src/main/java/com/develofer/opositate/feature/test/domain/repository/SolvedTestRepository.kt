package com.develofer.opositate.feature.test.domain.repository

import com.develofer.opositate.feature.profile.data.model.TestResult
import com.develofer.opositate.main.data.model.Result
import com.develofer.opositate.main.data.provider.TestType

interface SolvedTestRepository {
    suspend fun saveTestResult(testResult: TestResult, userId: String, isFirstTest: Boolean): Result<Unit>
    suspend fun getTestResult(solvedTestId: String, userId: String, testType: TestType): Result<TestResult?>
}