package com.develofer.opositate.domain.usecase

import com.develofer.opositate.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun logout() {
        authRepository.logout()
    }
}