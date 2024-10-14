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

    private val _isUserRetrieved = MutableStateFlow(false)
    val isUserRetrieved: StateFlow<Boolean> get() = _isUserRetrieved

    private var _currentUser: FirebaseUser? = null
    val currentUser: FirebaseUser? get() = _currentUser

    private val _isSystemUIVisible = MutableStateFlow(false)
    val isSystemUIVisible: StateFlow<Boolean> get() = _isSystemUIVisible

    init { getUserAuth() }

    private fun getUserAuth() {
        _currentUser = getUserUseCase.getUser()
        _isUserRetrieved.value = true
    }

    fun hideSystemUI() {
        _isSystemUIVisible.value = false
    }

    fun showSystemUI() {
        _isSystemUIVisible.value = true
    }

    fun toggleSystemUIVisibility() {
        _isSystemUIVisible.value = !_isSystemUIVisible.value
    }

}