package com.develofer.opositate.feature.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.feature.profile.domain.model.UserScoresVO
import com.develofer.opositate.feature.profile.domain.usecase.GetUserNameUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetUserScoresDocumentUseCase
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
    private val getUserScoresDocumentUseCase: GetUserScoresDocumentUseCase
) : ViewModel() {

    private val _userName = MutableStateFlow(EMPTY_STRING)
    val userName: StateFlow<String> get() = _userName

    private val _scores = MutableStateFlow(UserScoresVO())
    val scores: StateFlow<UserScoresVO> get() = _scores

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
            when (val result = getUserScoresDocumentUseCase()) {
                is Result.Success -> {
                    _scores.value = result.data
                }
                is Result.Error -> {
                    // Handle error with dialog
                }
                is Result.Loading -> { }
            }
        }
    }
}