package com.develofer.opositate.feature.profile

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateUserScoreDocumentUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        userRepository.createUserScoreDocument(onSuccess, onFailure)
    }
}