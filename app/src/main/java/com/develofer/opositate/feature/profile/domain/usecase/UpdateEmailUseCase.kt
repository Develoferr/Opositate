package com.develofer.opositate.feature.profile.domain.usecase

import com.develofer.opositate.feature.login.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(newEmail: String) = authRepository.updateEmail(newEmail)
}