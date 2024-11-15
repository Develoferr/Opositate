package com.develofer.opositate.feature.profile.domain.usecase

import com.develofer.opositate.R
import com.develofer.opositate.feature.profile.domain.mapper.toDomain
import com.develofer.opositate.feature.profile.domain.model.UserScores
import com.develofer.opositate.feature.profile.domain.repository.UserRepository
import com.develofer.opositate.main.data.model.Result
import com.develofer.opositate.main.data.provider.ResourceProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserScoresUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val getAbilityStringResIdUseCase: GetAbilityResIdUseCase,
    private val getTaskStringResIdUseCase: GetTaskStringResIdUseCase,
    private val resourceProvider: ResourceProvider
) {
    suspend operator fun invoke(): Result<UserScores> {
        return when (val result = userRepository.getUserScoreResponse()) {
            is Result.Success -> {
                result.data?.let {
                    val userScoresVO = result.data.toDomain(
                        getAbilityStringResIdUseCase,
                        getTaskStringResIdUseCase)

                    Result.Success(userScoresVO)
                } ?: Result.Error(Exception(
                    resourceProvider.getString(R.string.error_message__failed_to_convert_document)
                ))
            }
            is Result.Error -> Result.Error(result.exception)
            is Result.Loading -> Result.Loading
        }
    }
}