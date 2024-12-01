package com.develofer.opositate.feature.test.data.repository

import com.develofer.opositate.R
import com.develofer.opositate.feature.profile.data.model.TestResult
import com.develofer.opositate.feature.profile.data.model.TestResultDocument
import com.develofer.opositate.feature.test.domain.repository.SolvedTestRepository
import com.develofer.opositate.main.data.model.Result
import com.develofer.opositate.main.data.provider.ResourceProvider
import com.develofer.opositate.main.data.provider.TestType
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SolvedTestRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val resourceProvider: ResourceProvider
): SolvedTestRepository {

    override suspend fun saveTestResult(
        testResult: TestResult, userId: String, isFirstTest: Boolean
    ): Result<Unit> =
        if (userId.isBlank()) Result.Error(Exception(resourceProvider.getString(R.string.error_message__user_not_authenticated)))
        else {
            try {
                val testResultList = if (isFirstTest) listOf(testResult)
                else getTestResultList(userId, testResult.testType)?.solvedTests?.plus(testResult) ?: listOf(testResult)
                getSolvedTestCollection(testResult.testType).document(userId).set(mapOf(resourceProvider.getString(R.string.firebase_constant__solved_tests) to testResultList)).await()
                Result.Success(Unit)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getTestResult(solvedTestId: String, userId: String, testType: TestType): Result<TestResult?> =
        if (userId.isBlank()) Result.Error(Exception(resourceProvider.getString(R.string.error_message__user_not_authenticated)))
        else {
            try {
                Result.Success(getTestResultList(userId, testType)?.solvedTests?.find { it.id == solvedTestId })
            } catch (e: Exception) {
                Result.Error(e)
            }
        }


    private fun getSolvedTestCollection(testType: TestType): CollectionReference =
        when (testType) {
            TestType.ABILITY -> firestore.collection(resourceProvider.getString(R.string.firebase_constant__solved_tests_ability))
            TestType.GENERAL -> firestore.collection(resourceProvider.getString(R.string.firebase_constant__solved_tests_general))
            TestType.GROUP -> firestore.collection(resourceProvider.getString(R.string.firebase_constant__solved_tests_group))
            TestType.TASK -> firestore.collection(resourceProvider.getString(R.string.firebase_constant__solved_tests_task))
            TestType.CUSTOM -> firestore.collection(resourceProvider.getString(R.string.firebase_constant__solved_tests_custom))
        }

    private suspend fun getTestResultList(userId: String, testType: TestType): TestResultDocument? {
        val solvedTestDocument = getSolvedTestCollection(testType).document(userId).get().await()
        return if (solvedTestDocument.exists()) solvedTestDocument.toObject(TestResultDocument::class.java)
            else null
    }

}