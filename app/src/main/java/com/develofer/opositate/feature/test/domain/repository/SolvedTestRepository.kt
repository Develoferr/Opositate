package com.develofer.opositate.feature.test.domain.repository

import com.develofer.opositate.feature.profile.data.model.TestResult
import com.develofer.opositate.feature.profile.data.model.TestResultDocument

interface SolvedTestRepository {
    suspend fun saveTestResult(testResult: TestResult, userId: String, isFirstTest: Boolean)
    suspend fun getTestResult(solvedTestId: String, userId: String): TestResult?
    suspend fun getTestResultList(userId: String): TestResultDocument?
}