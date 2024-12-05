package com.develofer.opositate.feature.login.presentation.model

object TextFieldErrors {
    enum class ValidateFieldErrors {
        EMPTY_TEXT,
        INVALID_EMAIL,
        INVALID_PASSWORD,
        FIELDS_DO_NOT_MATCH,
        NONE
    }
}