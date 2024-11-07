package com.develofer.opositate.feature.profile.presentation.model

data class PsTestVO(
    val id: Int = 0,
    val name: String = "",
    var isEnabled: Boolean = false
) {
    val number: Int
        get() = id + 1
}