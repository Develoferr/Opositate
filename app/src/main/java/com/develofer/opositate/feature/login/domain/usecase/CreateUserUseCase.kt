package com.develofer.opositate.feature.login.domain.usecase

import com.develofer.opositate.feature.login.domain.repository.AuthRepository
import com.develofer.opositate.feature.profile.domain.usecase.CreateUserScoreDocumentUseCase
import com.develofer.opositate.feature.profile.domain.usecase.UpdateUserNameUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val updateUserNameUseCase: UpdateUserNameUseCase,
    private val createUserScoreDocumentUseCase: CreateUserScoreDocumentUseCase
) {
    operator fun invoke(username: String, email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        authRepository.createUser(
            email, password,
            onSuccess = {
                updateUserNameUseCase(
                    username,
                    onSuccess = {
                        createUserScoreDocumentUseCase(
                            onSuccess = {
                                onSuccess()
                            },
                            onFailure = {
                                onFailure(it)
                            }
                        )
                    },
                    onFailure = {
                        onFailure(it)
                    }
                )
            },
            onFailure = {
                onFailure(it)
            }
        )
    }
}