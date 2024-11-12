package com.develofer.opositate.feature.profile.data.repository

import com.develofer.opositate.feature.profile.domain.repository.UserRepository
import com.develofer.opositate.main.data.model.Result
import com.develofer.opositate.utils.StringConstants.EMPTY_STRING
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : UserRepository {
    override suspend fun getUserName(): Result<String> {
        val user = getUser()
        return if (user?.displayName.isNullOrEmpty()) {
            Result.Error(Exception("User has no display name"))
        } else {
            Result.Success(user?.displayName ?: EMPTY_STRING)
        }
    }

    override suspend fun getUserId(): Result<String> {
        val user = getUser()
        return if (user?.uid.isNullOrBlank()) {
            Result.Error(Exception("User is not authenticated"))
        } else {
            Result.Success(user?.uid ?: EMPTY_STRING)
        }
    }

    override suspend fun createUserScoreDocument(abilityIdList: List<Int>): Result<Unit> {
        val userId = getUser()?.uid
        return if (userId.isNullOrBlank()) {
            Result.Error(Exception("User is not authenticated"))
        } else {
            val userScoreDocument = getUserScoreDocumentReference(userId)
            val userScores: MutableList<Map<String, Any>> = getUserScoresMap(abilityIdList)
            suspendCoroutine { continuation ->
                userScoreDocument?.set(mapOf("level" to 0, "scores" to userScores))
                    ?.addOnCompleteListener { createTask ->
                        if (createTask.isSuccessful) {
                            continuation.resume(Result.Success(Unit))
                        } else {
                            val errorMessage = createTask.exception?.message ?: "Failed to create user score document"
                            continuation.resume(Result.Error(Exception(errorMessage)))
                        }
                    }
            }
        }
    }

    override suspend fun getUserScoreDocument(): Result<DocumentSnapshot> {
        val userId = getUser()?.uid
        return if (userId != null) {
                suspendCoroutine { continuation ->
                    val scoresCollection = firestore.collection("scores")
                    val documentReference = scoresCollection.document(userId)
                    documentReference.get()
                        .addOnSuccessListener { document ->
                            if (document.exists()) {
                                continuation.resume(Result.Success(document))
                            } else {
                                continuation.resume(Result.Error(Exception("User scores document does not exist")))
                            }
                        }
                        .addOnFailureListener { exception ->
                            continuation.resume(Result.Error(Exception(exception.message)))
                        }
                }
        } else {
            Result.Error(Exception("User is not authenticated"))
        }
    }

    private fun getUser() = auth.currentUser

    private fun getUserScoresMap(abilityIdList: List<Int>): MutableList<Map<String, Any>> {
        val userScores: MutableList<Map<String, Any>> = mutableListOf()
        abilityIdList.forEach { abilityId ->
            userScores.add(mapOf("abilityId" to abilityId, "startScore" to 0, "presentScore" to 0))
        }
        return userScores
    }

    private fun getUserScoreDocumentReference(userId: String?): DocumentReference? {
        val scoresCollection = firestore.collection("scores")
        val userScoreDocument = userId?.let { scoresCollection.document(it) }
        return userScoreDocument
    }
}