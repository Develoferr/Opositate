package com.develofer.opositate.feature.profile.domain.usecase

import com.develofer.opositate.feature.profile.domain.repository.UserRepository
import javax.inject.Inject

class GetUserIdUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): String =
        userRepository.getUserId()

}