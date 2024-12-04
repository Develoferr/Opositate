package com.develofer.opositate.main.data.model

sealed class UiResult {
    data object Idle : UiResult()
    data object Loading : UiResult()
    data object Success : UiResult()
    data class Error(val message: String) : UiResult()
}