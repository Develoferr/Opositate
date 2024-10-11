package com.develofer.opositate.domain.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun getUser(): FirebaseUser?
    fun createUser(username: String, email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun login(username: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun logout()
}