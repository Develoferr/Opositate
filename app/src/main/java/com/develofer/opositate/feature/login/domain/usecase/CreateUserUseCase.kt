package com.develofer.opositate.feature.login.domain.usecase

import com.develofer.opositate.feature.login.domain.repository.AuthRepository
import com.develofer.opositate.feature.profile.domain.usecase.CreateUserScoreDocumentUseCase
import com.develofer.opositate.feature.profile.domain.usecase.UpdateUserNameUseCase
import com.develofer.opositate.main.data.model.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val updateUserNameUseCase: UpdateUserNameUseCase,
    private val createUserScoreDocumentUseCase: CreateUserScoreDocumentUseCase
) {
    suspend operator fun invoke(username: String, email: String, password: String): Result<Unit> {
        // User creation Task
        val userCreationResult = authRepository.createUser(email, password)
        if (userCreationResult is Result.Error) return userCreationResult

        // Username save Task
        val updateUsernameResult = updateUserNameUseCase(username)
        if (updateUsernameResult is Result.Error) return updateUsernameResult

        // User score document creation Task
        val createUserScoreDocumentResult = createUserScoreDocumentUseCase()
        if (createUserScoreDocumentResult is Result.Error) return createUserScoreDocumentResult

        return Result.Success(Unit)
    }
}