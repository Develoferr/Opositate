package com.develofer.opositate.domain.usecase

import com.develofer.opositate.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun getUser(): FirebaseUser? =
        authRepository.getUser()
}