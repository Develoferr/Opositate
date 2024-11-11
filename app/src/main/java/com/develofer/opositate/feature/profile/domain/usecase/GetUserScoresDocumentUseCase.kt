package com.develofer.opositate.feature.profile.domain.usecase

import com.develofer.opositate.feature.profile.data.model.UserScoresResponse
import com.develofer.opositate.feature.profile.domain.model.ScoreVO
import com.develofer.opositate.feature.profile.domain.model.UserScoresVO
import com.develofer.opositate.feature.profile.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserScoresDocumentUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val getAbilityStringIdUseCase: GetAbilityNameUseCase
) {
    operator fun invoke(onSuccess: (UserScoresVO) -> Unit, onFailure: (String) -> Unit) {
        userRepository.getUserScoreDocument(
            onSuccess = { document ->
                document.toObject(UserScoresResponse::class.java)?.let { userScoresResponse ->
                    val userScoresVO = UserScoresVO(
                        userScoresResponse.level,
                        userScoresResponse.scores.map { scoreResponse ->
                            ScoreVO(
                                getAbilityStringIdUseCase(scoreResponse.abilityId),
                                scoreResponse.startScore,
                                scoreResponse.presentScore
                            )
                        }
                    )
                    onSuccess(userScoresVO)
                } ?: onFailure("Failed to convert document to UserScores")
            },
            onFailure = { error ->
                onFailure(error)
            }
        )
    }
}