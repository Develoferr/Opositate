package com.develofer.opositate.feature.test.domain.usecase

import com.develofer.opositate.feature.profile.domain.repository.UserRepository
import com.develofer.opositate.feature.profile.domain.usecase.GetAbilityResIdUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetTaskStringResIdUseCase
import com.develofer.opositate.feature.test.data.model.CompleteTestAsksList
import com.develofer.opositate.main.data.model.Result
import com.develofer.opositate.main.data.provider.ResourceProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTestAsksUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val getAbilityResIdUseCase: GetAbilityResIdUseCase,
    private val getTaskStringResIdUseCase: GetTaskStringResIdUseCase,
    private val resourceProvider: ResourceProvider
) {
    suspend operator fun invoke(): Result<CompleteTestAsksList?> {
        val getTestAsksListResult = userRepository.getTestAsksResponse()
        if (getTestAsksListResult is Result.Error) return Result.Error(getTestAsksListResult.exception)

        val testAsksList = (getTestAsksListResult as Result.Success).data
        return if (testAsksList == null) Result.Error(Exception("Test asks list is null"))
        else Result.Success(
            testAsksList.addNames(
                testAsksList,
                getAbilityResIdUseCase,
                getTaskStringResIdUseCase,
                resourceProvider
            )
        )
    }
}

private fun CompleteTestAsksList.addNames(
    testAsksList: CompleteTestAsksList,
    getAbilityResIdUseCase: GetAbilityResIdUseCase,
    getTaskStringResIdUseCase: GetTaskStringResIdUseCase,
    resourceProvider: ResourceProvider
) = CompleteTestAsksList(
    testByAbilityList = testAsksList.testByAbilityList.map {
        it.copy(
            abilityName = resourceProvider.getString(getAbilityResIdUseCase(it.abilityId)),
            tasksAsks = it.tasksAsks.map { taskAsks ->
                taskAsks.copy(
                    taskName = resourceProvider.getString(getTaskStringResIdUseCase(it.abilityId, taskAsks.taskId))
                )
            }
        )
    }
)