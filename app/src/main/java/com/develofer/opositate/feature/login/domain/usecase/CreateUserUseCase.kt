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
        // Create User Task
        val userCreationResult = authRepository.createUser(email, password)
        if (userCreationResult is Result.Error) return userCreationResult

        // Save Username Task
        val updateUsernameResult = updateUserNameUseCase(username)
        if (updateUsernameResult is Result.Error) return updateUsernameResult

        // Create User score document Task
        val createUserScoreDocumentResult = createUserScoreDocumentUseCase()
        return if (createUserScoreDocumentResult is Result.Error) Result.Error(createUserScoreDocumentResult.exception)
            else Result.Success(Unit)
    }
}