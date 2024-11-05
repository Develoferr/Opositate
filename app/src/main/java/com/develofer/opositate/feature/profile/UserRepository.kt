package com.develofer.opositate.feature.profile

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot

interface UserRepository {
    fun getUser(): FirebaseUser?
    fun getUserName(): String
    fun createUserScoreDocument(onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun getUserScoreDocument(onSuccess: (DocumentSnapshot) -> Unit, onFailure: (String) -> Unit)
}