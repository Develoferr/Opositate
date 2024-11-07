package com.develofer.opositate.feature.test

import com.develofer.opositate.feature.profile.TestResult
import com.develofer.opositate.feature.profile.TestResultDocument

interface SolvedTestRepository {
    suspend fun saveTestResult(testResult: TestResult, userId: String, isFirstTest: Boolean)
    suspend fun getTestResult(solvedTestId: String, userId: String): TestResult?
    suspend fun getTestResultList(userId: String): TestResultDocument?
}