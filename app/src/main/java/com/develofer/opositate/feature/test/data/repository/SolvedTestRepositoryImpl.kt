package com.develofer.opositate.feature.test.data.repository

import com.develofer.opositate.feature.profile.data.model.TestResult
import com.develofer.opositate.feature.profile.data.model.TestResultDocument
import com.develofer.opositate.feature.test.domain.repository.SolvedTestRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SolvedTestRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): SolvedTestRepository {

    private fun getSolvedTestCollection(): CollectionReference =
        firestore.collection("solvedTests")

    override suspend fun saveTestResult(testResult: TestResult, userId: String, isFirstTest: Boolean) {
        val testResultList =
            if (isFirstTest) listOf(testResult)
            else {
                getTestResultList(userId)?.solvedTests?.plus(testResult) ?: listOf(testResult)
            }

        getSolvedTestCollection().document(userId).set(
            mapOf("solvedTests" to testResultList)
        )

    }

    override suspend fun getTestResultList(userId: String): TestResultDocument? {
        val solvedTestDocument = getSolvedTestCollection().document(userId).get().await()
        return if (solvedTestDocument.exists()) solvedTestDocument.toObject(TestResultDocument::class.java)
            else null
    }

    override suspend fun getTestResult(solvedTestId: String, userId: String): TestResult? =
        getTestResultList(userId)?.solvedTests?.find { it.id == solvedTestId }

}