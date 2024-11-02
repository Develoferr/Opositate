package com.develofer.opositate.feature.login.domain.usecase

import com.develofer.opositate.feature.login.domain.repository.AuthRepository
import com.develofer.opositate.feature.profile.CreateUserScoreDocumentUseCase
import com.develofer.opositate.feature.profile.UpdateUserNameUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val updateUserNameUseCase: UpdateUserNameUseCase,
    private val createUserScoreDocumentUseCase: CreateUserScoreDocumentUseCase
) {
    fun createUser(username: String, email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        authRepository.createUser(
            username, email, password,
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