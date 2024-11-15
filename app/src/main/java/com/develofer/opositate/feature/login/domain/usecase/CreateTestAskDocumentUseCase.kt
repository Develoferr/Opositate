package com.develofer.opositate.feature.login.domain.usecase

import com.develofer.opositate.feature.profile.domain.repository.UserRepository
import com.develofer.opositate.feature.profile.domain.usecase.GetAbilityIdListUseCase
import com.develofer.opositate.main.data.model.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateTestAskDocumentUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val getAbilityIdListUseCase: GetAbilityIdListUseCase
) {
    suspend operator fun invoke(): Result<Unit> {
        val abilityIdList = getAbilityIdListUseCase()
        return userRepository.createTestAsksDocument(abilityIdList)
    }
}
