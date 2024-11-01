package com.develofer.opositate.feature.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.feature.profile.GetUserNameUseCase
import com.develofer.opositate.feature.profile.GetUserScoresDocument
import com.develofer.opositate.feature.profile.UserScores
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserNameUseCase: GetUserNameUseCase,
    private val getUserScoresDocument: GetUserScoresDocument
) : ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> get() = _userName

    private val _scores = MutableStateFlow(UserScores())
    val scores: StateFlow<UserScores> get() = _scores

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
            getUserScoresDocument(
                onSuccess = {
                    _scores.value = it
                },
                onFailure = {}
            )
        }
    }
}