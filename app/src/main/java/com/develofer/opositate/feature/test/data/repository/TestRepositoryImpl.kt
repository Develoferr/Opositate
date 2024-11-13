package com.develofer.opositate.feature.test.data.repository

import com.develofer.opositate.R
import com.develofer.opositate.feature.profile.data.model.PsTest
import com.develofer.opositate.feature.profile.presentation.model.PsTestVO
import com.develofer.opositate.feature.test.domain.repository.TestRepository
import com.develofer.opositate.main.data.model.Result
import com.develofer.opositate.main.data.provider.ResourceProvider
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val resourceProvider: ResourceProvider
): TestRepository {

    override suspend fun getTestList(): Result<List<PsTestVO>> {
        return try {
            val voTestList = getTestCollection().get().await().map {
                it.toObject(PsTestVO::class.java).apply { isEnabled = true }
            }
            Result.Success(voTestList)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getTest(id: String): Result<PsTest?> {
        return try {
            val document = getTestCollection().document(id).get().await()
            val test = if (document.exists()) document.toObject(PsTest::class.java) else null
            Result.Success(test)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private fun getTestCollection(): CollectionReference =
        firestore.collection(resourceProvider.getString(R.string.firebase_constant_tests))
}