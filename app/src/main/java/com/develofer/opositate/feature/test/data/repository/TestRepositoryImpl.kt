package com.develofer.opositate.feature.test.data.repository

import com.develofer.opositate.R
import com.develofer.opositate.feature.profile.presentation.model.PsTestDocumentResponse
import com.develofer.opositate.feature.profile.presentation.model.PsTestResponse
import com.develofer.opositate.feature.test.domain.repository.TestRepository
import com.develofer.opositate.main.data.model.Result
import com.develofer.opositate.main.data.provider.ResourceProvider
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TASKS_PER_ABILITY = 8

class TestRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val resourceProvider: ResourceProvider
): TestRepository {

    override suspend fun getTestList(): Result<List<PsTestDocumentResponse>> {
        return try {
            val psTestResponseList = getTestCollection().get().await().map {
                it.toObject(PsTestDocumentResponse::class.java)
            }
            Result.Success(psTestResponseList)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getTestListByAbility(abilityId: Int): Result<List<PsTestDocumentResponse>> {
        return try {
            val testPerTaskList = mutableListOf<PsTestDocumentResponse>()
            for (i in 1..TASKS_PER_ABILITY) {
                val documentId = "${abilityId}_${i - 1}"
                val document = getTestCollection().document(documentId).get().await()
                val testList = if (document.exists()) document.toObject(PsTestDocumentResponse::class.java) else null
                testList?.let { testPerTaskList.add(testList) }
            }
            Result.Success(testPerTaskList.toList())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getTestListByTask(abilityId: Int, abilityTaskId: Int): Result<List<PsTestResponse>> {
        return try {
            val documentId = "${abilityId}_${abilityTaskId}"
            val document = getTestCollection().document(documentId).get().await()
            val testList = if (document.exists()) document.toObject(PsTestDocumentResponse::class.java) else null
            Result.Success(testList?.tests ?: emptyList())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getTest(id: String, abilityId: Int, abilityTaskId: Int): Result<PsTestResponse?> {
        return try {
            val documentId = "${abilityId}_${abilityTaskId}"
            val document = getTestCollection().document(documentId).get().await()
            val testList = if (document.exists()) document.toObject(PsTestDocumentResponse::class.java) else null
            val test = testList?.tests?.find { it.id == id.toInt() }
                Result.Success(test)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private fun getTestCollection(): CollectionReference =
        firestore.collection(resourceProvider.getString(R.string.firebase_constant_tests))
}