package com.develofer.opositate.feature.profile

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : UserRepository {
    override fun getUser() = auth.currentUser

    override fun getUserName(): String = getUser()?.displayName ?: ""

    override fun createUserScoreDocument(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val userId = getUser()?.uid
        if (userId != null) {
            val firestore = FirebaseFirestore.getInstance()
            val scoresCollection = firestore.collection("scores")
            val userScoreDocument = scoresCollection.document(userId)
            userScoreDocument.set(
                mapOf(
                    "level" to 0,
                    "scores" to listOf(
                        mapOf("ability" to "1", "startScore" to 0, "presentScore" to 0,),
                        mapOf("ability" to "2", "startScore" to 0, "presentScore" to 0,),
                        mapOf("ability" to "3", "startScore" to 0, "presentScore" to 0,),
                        mapOf("ability" to "4", "startScore" to 0, "presentScore" to 0,),
                        mapOf("ability" to "5", "startScore" to 0, "presentScore" to 0,),
                        mapOf("ability" to "6", "startScore" to 0, "presentScore" to 0,),
                        mapOf("ability" to "7", "startScore" to 0, "presentScore" to 0,),
                        mapOf("ability" to "8", "startScore" to 0, "presentScore" to 0,),
                    )
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
            val firestore = FirebaseFirestore.getInstance()
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