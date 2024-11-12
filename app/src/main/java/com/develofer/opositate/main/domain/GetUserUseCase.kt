package com.develofer.opositate.main.domain

import com.develofer.opositate.feature.login.domain.repository.AuthRepository
import com.develofer.opositate.main.data.model.Result
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Result<FirebaseUser?> =
        authRepository.getUser()
}