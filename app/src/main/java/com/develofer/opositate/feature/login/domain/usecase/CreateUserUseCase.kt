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
    private val createUserScoreDocumentUseCase: CreateUserScoreDocumentUseCase,
    private val createTestAsksDocumentUseCase: CreateTestAskDocumentUseCase
) {
    suspend operator fun invoke(username: String, email: String, password: String): Result<Unit> {
        // Create User Task
        val userCreationResult = authRepository.createUser(email, password)
        if (userCreationResult is Result.Error) return userCreationResult

        // Save Username Task
        val updateUsernameResult = updateUserNameUseCase(username)
        if (updateUsernameResult is Result.Error) return updateUsernameResult

        // Create User Score document Task
        val createUserScoreDocumentResult = createUserScoreDocumentUseCase()
        if (createUserScoreDocumentResult is Result.Error) return createUserScoreDocumentResult

        // Create Test Asks document Task
        val createTestAsksDocumentResult = createTestAsksDocumentUseCase()
        return if (createTestAsksDocumentResult is Result.Error) createTestAsksDocumentResult
            else Result.Success(Unit)
    }
}