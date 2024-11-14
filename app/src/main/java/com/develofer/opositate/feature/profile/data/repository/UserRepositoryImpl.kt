package com.develofer.opositate.feature.profile.data.repository

import com.develofer.opositate.R
import com.develofer.opositate.feature.profile.domain.repository.UserRepository
import com.develofer.opositate.main.data.model.Result
import com.develofer.opositate.main.data.provider.ResourceProvider
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
    private val firestore: FirebaseFirestore,
    private val resourceProvider: ResourceProvider
) : UserRepository {
    override suspend fun getUserName(): Result<String> {
        val user = getUser()
        return if (user?.displayName.isNullOrEmpty()) {
            Result.Error(Exception(resourceProvider.getString(R.string.error_message_no_display_name)))
        } else {
            Result.Success(user?.displayName ?: EMPTY_STRING)
        }
    }

    override suspend fun getUserId(): Result<String> {
        val user = getUser()
        return if (user?.uid.isNullOrBlank()) {
            Result.Error(Exception(resourceProvider.getString(R.string.error_message_user_not_authenticated)))
        } else {
            Result.Success(user?.uid ?: EMPTY_STRING)
        }
    }

    override suspend fun createUserScoreDocument(abilityIdList: List<Map<String, Any>>): Result<Unit> {
        val userId = getUser()?.uid
        return if (userId.isNullOrBlank()) {
            Result.Error(Exception(resourceProvider.getString(R.string.error_message_user_not_authenticated)))
        } else {
            val userScoreDocument = getUserScoreDocumentReference(userId)
            val userScores: List<Map<String, Any>> = getUserScoresMap(abilityIdList)
            suspendCoroutine { continuation ->
                userScoreDocument?.set(mapOf(
                    resourceProvider.getString(R.string.firebase_constant_level) to 0,
                    resourceProvider.getString(R.string.firebase_constant_scores) to userScores)
                )?.addOnCompleteListener { createTask ->
                    if (createTask.isSuccessful) {
                        continuation.resume(Result.Success(Unit))
                    } else {
                        val errorMessage = createTask.exception?.message ?: resourceProvider.getString(R.string.error_message_create_score_document_failed)
                        continuation.resume(Result.Error(Exception(errorMessage)))
                    }
                }
            } ?: Result.Error(Exception(resourceProvider.getString(R.string.error_message_create_score_document_failed)))
        }
    }

    override suspend fun getUserScoreDocument(): Result<DocumentSnapshot> {
        val userId = getUser()?.uid
        return if (userId != null) {
                suspendCoroutine { continuation ->
                    val scoresCollection = firestore.collection(resourceProvider.getString(R.string.firebase_constant_scores))
                    val documentReference = scoresCollection.document(userId)
                    documentReference.get()
                        .addOnSuccessListener { document ->
                            if (document.exists()) {
                                continuation.resume(Result.Success(document))
                            } else {
                                continuation.resume(Result.Error(Exception(resourceProvider.getString(R.string.error_message_no_scores_document))))
                            }
                        }
                        .addOnFailureListener { exception ->
                            continuation.resume(Result.Error(Exception(exception.message)))
                        }
                }
        } else {
            Result.Error(Exception(resourceProvider.getString(R.string.error_message_user_not_authenticated)))
        }
    }

    private fun getUser() = auth.currentUser

    private fun getUserScoresMap(abilityIdList: List<Map<String, Any>>): List<Map<String, Any>> {
        val userScores: MutableList<Map<String, Any>> = mutableListOf()

        abilityIdList.forEach { abilityId ->
            val abilityIdValue = abilityId["abilityId"] as? Int ?: return@forEach
            val tasks = abilityId["tasks"] as? List<Int> ?: return@forEach

            val tasksWithScores = tasks.map { task ->
                mapOf(
                    "taskId" to (task),
                    "startScore" to 0,
                    "presentScore" to 0
                )
            }

            userScores.add(mapOf(
                resourceProvider.getString(R.string.firebase_constant_ability_id) to abilityIdValue,
                resourceProvider.getString(R.string.firebase_constant_start_score) to 0,
                resourceProvider.getString(R.string.firebase_constant_present_score) to 0,
                "tasks" to tasksWithScores
            ))
        }

        return userScores.toList()
    }

    private fun getUserScoreDocumentReference(userId: String?): DocumentReference? {
        val scoresCollection = firestore.collection(resourceProvider.getString(R.string.firebase_constant_scores))
        val userScoreDocument = userId?.let { scoresCollection.document(it) }
        return userScoreDocument
    }
}