package com.develofer.opositate.feature.login.domain.usecase

import com.develofer.opositate.feature.login.domain.repository.AuthRepository
import com.develofer.opositate.main.data.model.Result
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String): Result<Unit> =
        authRepository.sendPasswordResetEmail(email)
}