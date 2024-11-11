package com.develofer.opositate.feature.login.domain.usecase

import com.develofer.opositate.feature.login.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        authRepository.login(email, password, onSuccess, onFailure)
    }
}
