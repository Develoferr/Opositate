package com.develofer.opositate.feature.profile.domain.usecase

import com.develofer.opositate.feature.login.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateUserNameUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(username: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        authRepository.updateUsername(username, onSuccess, onFailure)
    }
}