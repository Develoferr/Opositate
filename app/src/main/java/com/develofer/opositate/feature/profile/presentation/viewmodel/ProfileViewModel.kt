package com.develofer.opositate.feature.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.feature.login.domain.usecase.ResetPasswordUseCase
import com.develofer.opositate.feature.profile.domain.model.UserScores
import com.develofer.opositate.feature.profile.domain.usecase.GetAbilityGroupIdUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetGroupAbilityResIdUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetGroupResIdIconUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetUserEmailUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetUserNameUseCase
import com.develofer.opositate.feature.profile.domain.usecase.GetUserScoresUseCase
import com.develofer.opositate.feature.profile.domain.usecase.UpdateEmailUseCase
import com.develofer.opositate.feature.profile.domain.usecase.UpdateUserNameUseCase
import com.develofer.opositate.feature.profile.model.ProfileUiState
import com.develofer.opositate.feature.profile.presentation.model.ProfileDialogType
import com.develofer.opositate.feature.profile.utils.toUserScoresByGroupList
import com.develofer.opositate.main.data.model.Result
import com.develofer.opositate.main.data.model.UiResult
import com.develofer.opositate.main.domain.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserNameUseCase: GetUserNameUseCase,
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val getUserScoresUseCase: GetUserScoresUseCase,
    private val getGroupAbilityUseCase: GetGroupAbilityResIdUseCase,
    private val getGroupIdUseCase: GetAbilityGroupIdUseCase,
    private val getGroupIdIcon: GetGroupResIdIconUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val updateUserNameUseCase: UpdateUserNameUseCase,
    private val updateEmailUseCase: UpdateEmailUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun showDialog(dialogType: ProfileDialogType) {
        _uiState.value.dialogStateCoordinator.showDialog(dialogType)
    }

    fun hideDialog() {
        _uiState.value.dialogStateCoordinator.hideDialog()

    }

    init {
        fetchUserName()
        fetchUserEmail()
        fetchScores()
    }

    private fun fetchUserName() {
        viewModelScope.launch {
            when (val result = getUserNameUseCase()) {
                is Result.Success -> {
                    _uiState.update { it.copy(userName = result.data) }
                }
                is Result.Error -> {
                    // Handle error with dialog
                }
                is Result.Loading -> { }
            }
        }
    }

    private fun fetchUserEmail() {
        viewModelScope.launch {
            when (val result = getUserEmailUseCase()) {
                is Result.Success -> {
                    _uiState.update { it.copy(userEmail = result.data) }
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
                    _uiState.update { it.copy(userScores = result.data) }
                    _uiState.update { it.copy(scoresByGroup = fetchScoresByGroup(result.data)) }
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

    fun updateUserName(newUserName: String) {
        _uiState.update {it.copy(updateUserNameResult = UiResult.Loading)}
        viewModelScope.launch {
            when (val result = updateUserNameUseCase(newUserName)) {
                is Result.Success -> {
                    _uiState.update {it.copy(userName = newUserName)}
                    _uiState.update {it.copy(updateUserNameResult = UiResult.Success)}
                }
                is Result.Error -> {
                    val error = result.exception.message ?: "An error occurred"
                    _uiState.update {it.copy(updateUserNameResult = UiResult.Error(error))}
                }
                is Result.Loading -> { }
            }
        }
    }

    fun updateEmail(newEmail: String) {
        _uiState.update {it.copy(updateEmailResult = UiResult.Loading)}
        viewModelScope.launch {
            when (val result = updateEmailUseCase(newEmail)) {
                is Result.Success -> {
                    _uiState.update {it.copy(userEmail = newEmail)}
                    _uiState.update {it.copy(updateEmailResult = UiResult.Success)}
                }
                is Result.Error -> {
                    val error = result.exception.message ?: "An error occurred"
                    _uiState.update {it.copy(updateEmailResult = UiResult.Error(error))}
                }
                is Result.Loading -> { }
            }
        }
    }

    fun updatePassword(newPassword: String) {
        _uiState.update {it.copy(updatePasswordResult = UiResult.Loading)}
        viewModelScope.launch {
            when (resetPasswordUseCase(newPassword)) {
                is Result.Success -> {
                    _uiState.update {it.copy(updatePasswordResult = UiResult.Success)}
                }
                is Result.Error -> {
                    _uiState.update {it.copy(updatePasswordResult = UiResult.Error("An error occurred"))}
                }

                is Result.Loading -> {}
            }
        }
    }

    fun logOut() {
        hideDialog()
        viewModelScope.launch {
            when (logoutUseCase()) {
                is Result.Success -> {}
                is Result.Error -> {
                    // Handle error with dialog
                }
                is Result.Loading -> {}
            }
        }
    }

    fun cleanUpState() {
        _uiState.update {
            it.copy(
                updateUserNameResult = UiResult.Idle,
                updateEmailResult = UiResult.Idle,
                updatePasswordResult = UiResult.Idle
            )
        }
    }
}