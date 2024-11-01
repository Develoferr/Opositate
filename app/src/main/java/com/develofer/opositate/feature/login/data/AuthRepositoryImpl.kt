package com.develofer.opositate.feature.login.data

import com.develofer.opositate.feature.login.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override fun getUser() = auth.currentUser

    override fun getUserName(): String = getUser()?.displayName ?: ""

    override fun createUser(username: String, email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateUsername(username, onSuccess, onFailure)
                    createUserScoreDocument(onSuccess, onFailure)
                } else {
                    onFailure(task.exception?.message ?: "Registration failed")
                }
            }
        }
    }
    private fun createUserScoreDocument(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val userId = getUser()?.uid
        if (userId != null) {
            val firestore = FirebaseFirestore.getInstance()
            val scoresCollection = firestore.collection("scores")
            val userScoreDocument = scoresCollection.document(userId)
            userScoreDocument.set(
                mapOf(
                    "level" to 0,
                    "scores" to listOf(
                        mapOf(
                            "ability" to "1",
                            "start_score" to 0,
                            "present_score" to 0,
                        ),
                        mapOf(
                            "ability" to "2",
                            "start_score" to 0,
                            "present_score" to 0,
                        ),
                        mapOf(
                            "ability" to "3",
                            "start_score" to 0,
                            "present_score" to 0,
                        ),
                        mapOf(
                            "ability" to "4",
                            "start_score" to 0,
                            "present_score" to 0,
                        ),
                        mapOf(
                            "ability" to "5",
                            "start_score" to 0,
                            "present_score" to 0,
                        ),
                        mapOf(
                            "ability" to "6",
                            "start_score" to 0,
                            "present_score" to 0,
                        ),
                        mapOf(
                            "ability" to "7",
                            "start_score" to 0,
                            "present_score" to 0,
                        ),
                        mapOf(
                            "ability" to "8",
                            "start_score" to 0,
                            "present_score" to 0,
                        ),
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

    override fun updateUsername(
        username: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        auth.currentUser?.updateProfile(
            UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build()
        )?.addOnCompleteListener { updateTask ->
            if (updateTask.isSuccessful) {
                onSuccess()
            } else {
                onFailure(updateTask.exception?.message ?: "Profile update failed")
            }
        }
    }

    override fun login(username: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        if (username.isNotBlank() && password.isNotBlank()) {
            auth.signInWithEmailAndPassword(username, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception?.message ?: "Login failed")
                }
            }
        }
    }

    override fun sendPasswordResetEmail(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception?.message ?: "Failed to send reset email")
                }
            }
    }

    override fun logout() = auth.signOut()
}