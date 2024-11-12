package com.develofer.opositate.feature.test.domain.repository

import com.develofer.opositate.feature.profile.data.model.PsTest
import com.develofer.opositate.feature.profile.presentation.model.PsTestVO
import com.develofer.opositate.main.data.model.Result

interface TestRepository {
    suspend fun getTestList(): Result<List<PsTestVO>>
    suspend fun getTest(id: String): Result<PsTest?>
}