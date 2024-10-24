package com.develofer.opositate.login.domain.usecase

import com.develofer.opositate.login.domain.repository.AuthRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        authRepository.sendPasswordResetEmail(email, onSuccess, onFailure)
    }
}