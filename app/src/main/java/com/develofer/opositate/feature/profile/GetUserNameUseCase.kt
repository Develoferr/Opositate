package com.develofer.opositate.feature.profile

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserNameUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): String = userRepository.getUserName()
}