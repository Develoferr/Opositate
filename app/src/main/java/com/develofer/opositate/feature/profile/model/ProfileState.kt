package com.develofer.opositate.feature.profile.model

import com.develofer.opositate.feature.profile.domain.model.UserScores
import com.develofer.opositate.feature.profile.domain.model.UserScoresByGroup
import com.develofer.opositate.feature.profile.presentation.model.ProfileDialogType
import com.develofer.opositate.main.coordinator.DialogStateCoordinator
import com.develofer.opositate.main.data.model.UiResult

data class ProfileUiState(
    val userName: String = "",
    val userEmail: String = "",
    val userScores: UserScores = UserScores(),
    val scoresByGroup: List<UserScoresByGroup> = emptyList(),
    val dialogStateCoordinator: DialogStateCoordinator<ProfileDialogType> = DialogStateCoordinator(),
    val updateUserNameResult: UiResult = UiResult.Idle,
    val updateEmailResult: UiResult = UiResult.Idle,
    val updatePasswordResult: UiResult = UiResult.Idle
)