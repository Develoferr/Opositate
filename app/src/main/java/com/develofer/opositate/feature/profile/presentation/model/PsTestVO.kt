package com.develofer.opositate.feature.profile.presentation.model

import com.develofer.opositate.utils.StringConstants.EMPTY_STRING

data class PsTestVO(
    val id: Int = 0,
    val name: String = EMPTY_STRING,
    var isEnabled: Boolean = false
) {
    val number: Int
        get() = id + 1
}