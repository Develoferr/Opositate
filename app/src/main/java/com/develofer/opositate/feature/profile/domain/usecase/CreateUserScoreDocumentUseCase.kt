package com.develofer.opositate.feature.profile.domain.usecase

import com.develofer.opositate.feature.profile.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateUserScoreDocumentUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val getAbilityIdListUseCase: GetAbilityIdListUseCase
) {
    operator fun invoke(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val abilityIdList = getAbilityIdListUseCase()
        userRepository.createUserScoreDocument(onSuccess, onFailure, abilityIdList)
    }

}