package com.develofer.opositate.feature.profile.domain.repository

import com.develofer.opositate.main.data.model.Result
import com.google.firebase.firestore.DocumentSnapshot

interface UserRepository {
    suspend fun getUserName(): Result<String>
    suspend fun getUserId(): Result<String>
    suspend fun createUserScoreDocument(abilityIdList: List<Map<String, Any>>): Result<Unit>
    suspend fun getUserScoreDocument(): Result<DocumentSnapshot>
}