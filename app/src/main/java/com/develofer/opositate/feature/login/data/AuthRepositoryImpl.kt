package com.develofer.opositate.feature.login.data

import com.develofer.opositate.feature.login.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override fun getUser() = auth.currentUser

    override fun createUser(username: String, email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateUsername(username, onSuccess, onFailure)
                    onSuccess()
                } else {
                    onFailure(task.exception?.message ?: "Registration failed")
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

    override fun logout() {
        auth.signOut()
    }
}