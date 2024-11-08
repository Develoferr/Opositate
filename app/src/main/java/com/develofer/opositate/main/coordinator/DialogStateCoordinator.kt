package com.develofer.opositate.main.coordinator

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DialogStateCoordinator<T : Enum<T>>() {
    private val _dialogState = MutableStateFlow(DialogState<T>())
    val dialogState: StateFlow<DialogState<T>> get() = _dialogState
    fun showDialog(dialogType: T) {
        _dialogState.value = DialogState(true, dialogType)
    }

    fun hideDialog() {
        _dialogState.value = DialogState(false)
    }
}

data class DialogState<T : Enum<T>>(
    val isVisible: Boolean = false,
    val dialogType: T? = null
)
