package com.develofer.opositate.feature.profile

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserScoresDocument @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(onSuccess: (UserScores) -> Unit, onFailure: (String) -> Unit) {
        userRepository.getUserScoreDocument(
            onSuccess = { document ->
                document.toObject(UserScores::class.java)?.let {
                    onSuccess(it)
                } ?: onFailure("Failed to convert document to UserScores")
            },
            onFailure = { error ->
                onFailure(error)
            }
        )
    }
}