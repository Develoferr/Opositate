package com.develofer.opositate.feature.profile

import com.develofer.opositate.feature.login.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserNameUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): String = authRepository.getUserName()
}