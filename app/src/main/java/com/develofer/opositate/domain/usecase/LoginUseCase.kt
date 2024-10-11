package com.develofer.opositate.domain.usecase

import com.develofer.opositate.domain.repository.AuthRepository

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun login(username: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        authRepository.login(username, password, onSuccess, onFailure)
    }
}
