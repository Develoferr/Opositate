package com.develofer.opositate.feature.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.feature.profile.domain.model.UserScoresVO
import com.develofer.opositate.feature.profile.domain.usecase.GetUserNameUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetUserScoresDocumentUseCase
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

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> get() = _userName

    private val _scores = MutableStateFlow(UserScoresVO())
    val scores: StateFlow<UserScoresVO> get() = _scores

    init {
        fetchUserName()
        fetchScores()
    }

    private fun fetchUserName() {
        viewModelScope.launch {
            _userName.value = getUserNameUseCase()
        }
    }

    private fun fetchScores() {
        viewModelScope.launch {
            getUserScoresDocumentUseCase(
                onSuccess = {
                    _scores.value = it
                },
                onFailure = {}
            )
        }
    }
}