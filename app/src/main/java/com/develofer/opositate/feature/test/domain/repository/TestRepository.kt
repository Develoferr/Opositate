package com.develofer.opositate.feature.test.domain.repository

import com.develofer.opositate.feature.profile.data.model.PsTest
import com.develofer.opositate.feature.profile.presentation.model.PsTestVO

interface TestRepository {
    suspend fun getTestList(): List<PsTestVO>
    suspend fun getTest(id: String): PsTest?
}