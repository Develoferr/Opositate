package com.develofer.opositate.feature.login.data

import com.develofer.opositate.feature.login.domain.repository.AuthRepository
import com.develofer.opositate.main.data.model.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override fun getUser(): Result<FirebaseUser?> {
        return auth.currentUser?.let {
            Result.Success(it)
        } ?: Result.Error(Exception("No authenticated user"))
    }

    override suspend fun createUser(email: String, password: String): Result<Unit> =
        if (email.isNotBlank() && password.isNotBlank()) {
            suspendCoroutine { continuation ->
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { createTask ->
                    if (createTask.isSuccessful) {
                        continuation.resume(Result.Success(Unit))
                    } else {
                        val errorMessage = createTask.exception?.message ?: "Registration failed"
                        continuation.resume(Result.Error(Exception(errorMessage)))
                    }
                }
            }
        } else Result.Error(Exception("Email and password must not be blank"))

    override suspend fun updateUsername(username: String): Result<Unit> =
        if (username.isNotBlank()) {
            suspendCoroutine { continuation ->
                auth.currentUser?.updateProfile(
                    UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()
                )?.addOnCompleteListener { updateTask ->
                    if (updateTask.isSuccessful) {
                        continuation.resume(Result.Success(Unit))
                    } else {
                        val errorMessage = updateTask.exception?.message ?: "Profile update failed"
                        continuation.resume(Result.Error(Exception(errorMessage)))
                    }
                }
            }
        } else Result.Error(Exception("Username must not be blank"))


    override suspend fun login(email: String, password: String): Result<Unit> =
        if (email.isNotBlank() && password.isNotBlank()) {
            suspendCoroutine { continuation ->
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { loginTask ->
                    if (loginTask.isSuccessful) {
                        continuation.resume(Result.Success(Unit))
                    } else {
                        val errorMessage = loginTask.exception?.message ?: "Login failed"
                        continuation.resume(Result.Error(Exception(errorMessage)))
                    }
                }
            }
        } else Result.Error(Exception("Email and password must not be blank"))


    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> =
        if (email.isNotBlank()) {
            suspendCoroutine { continuation ->
                auth.sendPasswordResetEmail(email).addOnCompleteListener { sendTask ->
                    if (sendTask.isSuccessful) {
                        continuation.resume(Result.Success(Unit))
                    } else {
                        val errorMessage =
                            sendTask.exception?.message ?: "Failed to send reset email"
                        continuation.resume(Result.Error(Exception(errorMessage)))
                    }
                }
            }
        } else Result.Error(Exception("Email must not be blank"))

    override fun logout(): Result<Unit> {
        return try {
            auth.signOut()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}