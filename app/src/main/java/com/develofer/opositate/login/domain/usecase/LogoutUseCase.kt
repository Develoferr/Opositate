package com.develofer.opositate.login.domain.usecase

import com.develofer.opositate.login.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun logout() {
        authRepository.logout()
    }
}