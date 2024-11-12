package com.develofer.opositate.feature.profile.domain.usecase

import com.develofer.opositate.feature.profile.data.model.UserScoresResponse
import com.develofer.opositate.feature.profile.domain.model.ScoreVO
import com.develofer.opositate.feature.profile.domain.model.UserScoresVO
import com.develofer.opositate.feature.profile.domain.repository.UserRepository
import com.develofer.opositate.main.data.model.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserScoresDocumentUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val getAbilityStringIdUseCase: GetAbilityNameUseCase
) {
    suspend operator fun invoke(): Result<UserScoresVO> {
        return when (val result = userRepository.getUserScoreDocument()) {
            is Result.Success -> {
                result.data.toObject(UserScoresResponse::class.java)?.let { userScoresResponse ->
                    val userScoresVO = UserScoresVO(
                        level = userScoresResponse.level,
                        scores = userScoresResponse.scores.map { scoreResponse ->
                            ScoreVO(
                                abilityName = getAbilityStringIdUseCase(scoreResponse.abilityId),
                                startScore = scoreResponse.startScore,
                                presentScore = scoreResponse.presentScore
                            )
                        }
                    )
                    Result.Success(userScoresVO)
                } ?: Result.Error(Exception("Failed to convert document to UserScores"))
            }
            is Result.Error -> Result.Error(result.exception)
            Result.Loading -> Result.Loading
        }
    }
}