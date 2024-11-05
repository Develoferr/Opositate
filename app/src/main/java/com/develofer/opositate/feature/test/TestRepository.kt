package com.develofer.opositate.feature.test

import com.develofer.opositate.feature.profile.PsTest
import com.develofer.opositate.feature.profile.PsTestVO

interface TestRepository {
    suspend fun getTestList(): List<PsTestVO>
    suspend fun getTest(id: String): PsTest?
}