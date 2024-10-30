package com.develofer.opositate.feature.login.domain.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun getUser(): FirebaseUser?
    fun getUserName(): String
    fun createUser(username: String, email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun updateUsername(username: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun login(username: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun sendPasswordResetEmail(email: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun logout()
}