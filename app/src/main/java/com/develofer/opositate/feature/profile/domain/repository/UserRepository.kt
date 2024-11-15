package com.develofer.opositate.feature.profile.domain.repository

import com.develofer.opositate.feature.profile.data.model.UserScoresResponse
import com.develofer.opositate.feature.test.domain.model.CompleteTestAsksList
import com.develofer.opositate.main.data.model.Result

interface UserRepository {
    suspend fun getUserName(): Result<String>
    suspend fun getUserId(): Result<String>
    suspend fun createUserScoreDocument(abilityIdList: List<Map<String, Any>>): Result<Unit>
    suspend fun createTestAsksDocument(abilityIdList: List<Map<String, Any>>): Result<Unit>
    suspend fun getUserScoreResponse(): Result<UserScoresResponse?>
    suspend fun getTestAsksResponse(): Result<CompleteTestAsksList?>
}