package com.develofer.opositate.feature.test.domain.usecase

import com.develofer.opositate.feature.test.presentation.model.PsTest
import com.develofer.opositate.main.data.model.Result
import com.develofer.opositate.main.data.provider.TestType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTestUseCase @Inject constructor(
    private val getTestTypeByIdUseCase: GetTestTypeByIdUseCase,
    private val getTestGeneralUseCase: GetTestGeneralUseCase,
    private val getTestByGroupUseCase: GetTestByGroupUseCase,
    private val getTestByAbilityUseCase: GetTestByAbilityUseCase,
    private val getTestByTaskUseCase: GetTestByTaskUseCase,
    private val getAbilityIdListFromGroupId: GetAbilityIdListFromGroupId
) {
    suspend operator fun invoke(testTypeId: Int, difficultId: Int?, groupId: Int?, abilityId: Int?, taskId: Int?): Result<PsTest?> {
        val testType: TestType = getTestTypeByIdUseCase(testTypeId)

        return when (testType) {
            TestType.GENERAL -> getTestGeneralUseCase(difficultId!!, null) // testId = 0..99 - 100 different tests
            TestType.GROUP -> getTestByGroupUseCase(getAbilityIdListFromGroupId(groupId!!), difficultId!!, null) // testId = 0..39 - 40 different tests
            TestType.ABILITY -> getTestByAbilityUseCase(difficultId!!, abilityId!!, null) // testId = 0..9 - 10 different tests
            TestType.TASK -> getTestByTaskUseCase(abilityId!!, difficultId!!, taskId!!, null) // testId = 0..1 - 2 different tests
            TestType.CUSTOM -> Result.Error(Exception("Custom tests are not supported yet"))
        }
    }
}