package com.develofer.opositate.feature.profile

import javax.inject.Inject

class GetUserIdUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): String =
        userRepository.getUserId()

}