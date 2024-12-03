package com.develofer.opositate.feature.profile.domain.usecase

import com.develofer.opositate.feature.login.domain.repository.AuthRepository
import com.develofer.opositate.main.data.model.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteAccountUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> = authRepository.deleteAccount()
}