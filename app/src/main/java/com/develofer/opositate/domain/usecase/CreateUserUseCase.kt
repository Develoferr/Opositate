package com.develofer.opositate.domain.usecase

import com.develofer.opositate.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun createUser(username: String, password: String, email: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        authRepository.createUser(username, password, email, onSuccess, onFailure)
    }
}