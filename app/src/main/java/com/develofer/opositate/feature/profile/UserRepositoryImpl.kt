package com.develofer.opositate.feature.profile

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : UserRepository {
    override fun getUser() = auth.currentUser

    override fun getUserName(): String = getUser()?.displayName ?: ""

    override fun getUserId(): String = getUser()?.uid ?: ""

    override fun createUserScoreDocument(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val userId = getUser()?.uid
        if (userId != null) {
            val scoresCollection = firestore.collection("scores")
            val userScoreDocument = scoresCollection.document(userId)
            val userScores: MutableList<Map<String, Any>> = mutableListOf()
            Abilities.entries.forEach { ability ->
                userScores.add(mapOf("abilityName" to ability.abilityName, "startScore" to 0, "presentScore" to 0))
            }
            userScoreDocument.set(
                mapOf(
                    "level" to 0,
                    "scores" to userScores
                )
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception?.message ?: "Failed to create user score document")
                }
            }
        }
    }

    override fun getUserScoreDocument(onSuccess: (DocumentSnapshot) -> Unit, onFailure: (String) -> Unit) {
        val userId = getUser()?.uid
        if (userId != null) {
            val scoresCollection = firestore.collection("scores")
            scoresCollection.document(userId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        onSuccess(document)
                    } else {
                        onFailure("User scores document does not exist")
                    }
                }
                .addOnFailureListener { exception ->
                    onFailure(exception.message ?: "Error getting user scores document")
                }
        } else {
            onFailure("User is not authenticated")
        }
    }
}