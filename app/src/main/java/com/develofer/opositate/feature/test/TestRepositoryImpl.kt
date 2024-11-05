package com.develofer.opositate.feature.test

import com.develofer.opositate.feature.profile.PsTest
import com.develofer.opositate.feature.profile.PsTestVO
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): TestRepository {

    private fun getTestCollection(): CollectionReference =
        firestore.collection("tests")

    override suspend fun getTestList(): List<PsTestVO> {
        val voTestList = mutableListOf<PsTestVO>()
        for (test in getTestCollection().get().await()) {
            voTestList.add(test.toObject(PsTestVO::class.java))
            voTestList.last().isEnabled = true
        }
        return voTestList
    }

    override suspend fun getTest(id: String): PsTest? {
        val document = getTestCollection().document(id).get().await()
        return if (document.exists()) document.toObject(PsTest::class.java)
            else null
    }

}