package com.develofer.opositate.feature.settings.domain.usecase

import com.develofer.opositate.feature.login.domain.repository.AuthRepository
import com.develofer.opositate.main.data.model.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReauthenticateUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> =
        authRepository.reauthenticate(email, password)
}