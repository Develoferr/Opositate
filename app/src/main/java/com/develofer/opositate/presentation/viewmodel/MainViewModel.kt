package com.develofer.opositate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.develofer.opositate.domain.usecase.GetUserUseCase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _isUserAuthFindOutFinished = MutableStateFlow(false)
    val isUserAuthFindOutFinished: StateFlow<Boolean> get() = _isUserAuthFindOutFinished

    init {
        checkUserAuth()
    }

    private fun checkUserAuth() {
        val user = currentUser
        _isUserAuthFindOutFinished.value = true

    }

    val currentUser: FirebaseUser?
        get() = getUserUseCase.getUser()

}