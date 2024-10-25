package com.develofer.opositate.login.data

import com.develofer.opositate.login.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
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
                    onSuccess()
                } else {
                    onFailure(task.exception?.message ?: "Registration failed")
                }
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