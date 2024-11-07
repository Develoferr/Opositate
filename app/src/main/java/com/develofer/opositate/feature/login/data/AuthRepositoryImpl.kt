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

    override fun createUser(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { createTask ->
            if (createTask.isSuccessful) {
                onSuccess()
            } else {
                onFailure(createTask.exception?.message ?: "Registration failed")
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
            auth.signInWithEmailAndPassword(username, password).addOnCompleteListener { loginTask ->
                if (loginTask.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(loginTask.exception?.message ?: "Login failed")
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
            .addOnCompleteListener { sendTask ->
                if (sendTask.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(sendTask.exception?.message ?: "Failed to send reset email")
                }
            }
    }

    override fun logout() = auth.signOut()
}