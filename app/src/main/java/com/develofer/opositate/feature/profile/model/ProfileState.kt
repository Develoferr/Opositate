package com.develofer.opositate.feature.profile.model

import com.develofer.opositate.feature.profile.domain.model.UserScores
import com.develofer.opositate.feature.profile.domain.model.UserScoresByGroup

data class ProfileScreenState(
    val userName: String,
    val userEmail: String,
    val userScores: UserScores,
    val scoresByGroup: List<UserScoresByGroup>
)