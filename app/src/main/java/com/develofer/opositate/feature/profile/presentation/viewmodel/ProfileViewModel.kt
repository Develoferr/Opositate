package com.develofer.opositate.feature.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.feature.profile.domain.model.UserScores
import com.develofer.opositate.feature.profile.domain.model.UserScoresByGroup
import com.develofer.opositate.feature.profile.domain.usecase.GetAbilityGroupIdUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetGroupAbilityResIdUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetGroupResIdIconUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetUserNameUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetUserScoresUseCase
import com.develofer.opositate.feature.profile.utils.toUserScoresByGroupList
import com.develofer.opositate.main.data.model.Result
import com.develofer.opositate.utils.StringConstants.EMPTY_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserNameUseCase: GetUserNameUseCase,
    private val getUserScoresUseCase: GetUserScoresUseCase,
    private val getGroupAbilityUseCase: GetGroupAbilityResIdUseCase,
    private val getGroupIdUseCase: GetAbilityGroupIdUseCase,
    private val getGroupIdIcon: GetGroupResIdIconUseCase
) : ViewModel() {

    private val _userName = MutableStateFlow(EMPTY_STRING)
    val userName: StateFlow<String> get() = _userName

    private val _scores = MutableStateFlow(UserScores())
    val scores: StateFlow<UserScores> get() = _scores

    private val _scoresByGroup = MutableStateFlow(emptyList<UserScoresByGroup>())
    val scoresByGroup: StateFlow<List<UserScoresByGroup>> get() = _scoresByGroup

    init {
        fetchUserName()
        fetchScores()
    }

    private fun fetchUserName() {
        viewModelScope.launch {
            when (val result = getUserNameUseCase()) {
                is Result.Success -> {
                    _userName.value = result.data
                }
                is Result.Error -> {
                    // Handle error with dialog
                }
                is Result.Loading -> { }
            }
        }
    }

    private fun fetchScores() {
        viewModelScope.launch {
            when (val result = getUserScoresUseCase()) {
                is Result.Success -> {
                    _scores.value = result.data
                    _scoresByGroup.value = fetchScoresByGroup(result.data)
                }
                is Result.Error -> {
                    // Handle error with dialog
                }
                is Result.Loading -> { }
            }
        }
    }

    private fun fetchScoresByGroup(userScores: UserScores) =
        userScores.scores.toUserScoresByGroupList(
            getGroupAbilityUseCase,
            getGroupIdUseCase,
            getGroupIdIcon
        )
}