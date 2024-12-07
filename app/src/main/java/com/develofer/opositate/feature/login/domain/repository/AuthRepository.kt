package com.develofer.opositate.feature.login.domain.repository

import com.develofer.opositate.main.data.model.Result
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun getUser(): Result<FirebaseUser?>
    suspend fun createUser(email: String, password: String): Result<Unit>
    suspend fun updateUsername(username: String): Result<Unit>
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun sendPasswordResetEmail(email: String): Result<Unit>
    suspend fun updateEmail(newEmail: String): Result<Unit>
    suspend fun deleteAccount(): Result<Unit>
    suspend fun reauthenticate(email: String, password: String): Result<Unit>
    fun logout(): Result<Unit>
}