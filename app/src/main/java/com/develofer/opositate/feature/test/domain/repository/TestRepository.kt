package com.develofer.opositate.feature.test.domain.repository

import com.develofer.opositate.feature.test.presentation.model.PsTest
import com.develofer.opositate.main.data.model.Result

interface TestRepository {
    suspend fun getTestByAbility(abilityId: Int, difficultyId: Int, testId: Int?): Result<PsTest?>
    suspend fun getTestByGroup(abilityIdList: List<Int>, difficultyId: Int, testId: Int?): Result<PsTest?>
    suspend fun getTestByTask(abilityId: Int, taskId: Int, testId: Int, difficultyId: Int): Result<PsTest?>
    suspend fun getTestGeneral(difficultyId: Int, testId: Int): Result<PsTest?>
}