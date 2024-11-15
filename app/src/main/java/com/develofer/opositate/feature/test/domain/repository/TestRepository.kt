package com.develofer.opositate.feature.test.domain.repository

import com.develofer.opositate.feature.profile.data.model.PsTest
import com.develofer.opositate.feature.profile.presentation.model.PsTestDocumentResponse
import com.develofer.opositate.feature.profile.presentation.model.PsTestResponse
import com.develofer.opositate.main.data.model.Result

interface TestRepository {
    suspend fun getTestList(): Result<List<PsTestDocumentResponse>>
    suspend fun getTestListByAbility(abilityId: Int): Result<List<PsTestDocumentResponse>>
    suspend fun getTestListByTask(abilityId: Int, abilityTaskId: Int): Result<List<PsTestResponse>>
    suspend fun getTest(id: String, abilityId: Int, abilityTaskId: Int): Result<PsTest?>
}