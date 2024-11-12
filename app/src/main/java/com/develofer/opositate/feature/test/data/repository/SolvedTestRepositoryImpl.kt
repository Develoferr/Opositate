package com.develofer.opositate.feature.test.data.repository

import com.develofer.opositate.feature.profile.data.model.TestResult
import com.develofer.opositate.feature.profile.data.model.TestResultDocument
import com.develofer.opositate.feature.test.domain.repository.SolvedTestRepository
import com.develofer.opositate.main.data.model.Result
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SolvedTestRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): SolvedTestRepository {

    override suspend fun saveTestResult(
        testResult: TestResult, userId: String, isFirstTest: Boolean
    ): Result<Unit> =
        if (userId.isBlank()) Result.Error(Exception("User is not authenticated"))
        else {
            try {
                val testResultList = if (isFirstTest) listOf(testResult)
                else getTestResultList(userId)?.solvedTests?.plus(testResult) ?: listOf(testResult)
                getSolvedTestCollection().document(userId).set(mapOf("solvedTests" to testResultList)).await()
                Result.Success(Unit)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getTestResult(solvedTestId: String, userId: String): Result<TestResult?> =
        if (userId.isBlank()) Result.Error(Exception("User is not authenticated"))
        else {
            try {
                Result.Success(getTestResultList(userId)?.solvedTests?.find { it.id == solvedTestId })
            } catch (e: Exception) {
                Result.Error(e)
            }
        }


    private fun getSolvedTestCollection(): CollectionReference =
        firestore.collection("solvedTests")

    private suspend fun getTestResultList(userId: String): TestResultDocument? {
        val solvedTestDocument = getSolvedTestCollection().document(userId).get().await()
        return if (solvedTestDocument.exists()) solvedTestDocument.toObject(TestResultDocument::class.java)
            else null
    }

}