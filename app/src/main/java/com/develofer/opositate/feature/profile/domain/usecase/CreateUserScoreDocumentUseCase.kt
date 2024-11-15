package com.develofer.opositate.feature.profile.domain.usecase

import com.develofer.opositate.feature.profile.domain.repository.UserRepository
import com.develofer.opositate.main.data.model.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateUserScoreDocumentUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val getAbilityIdListUseCase: GetAbilityIdListUseCase
) {
    suspend operator fun invoke(): Result<Unit> {
        val abilityIdList = getAbilityIdListUseCase()
        return userRepository.createUserScoreDocument(abilityIdList)
    }
}