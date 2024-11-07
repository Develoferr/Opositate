package com.develofer.opositate.feature.profile.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot

interface UserRepository {
    fun getUser(): FirebaseUser?
    fun getUserName(): String
    fun getUserId(): String
    fun createUserScoreDocument(onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun getUserScoreDocument(onSuccess: (DocumentSnapshot) -> Unit, onFailure: (String) -> Unit)
}