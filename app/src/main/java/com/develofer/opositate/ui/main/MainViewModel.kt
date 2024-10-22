package com.develofer.opositate.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    private val _isUserNotRetrieved = MutableStateFlow(true)
    val isUserNotRetrieved: StateFlow<Boolean> get() = _isUserNotRetrieved

    private var _currentUser: FirebaseUser? = null
    val currentUser: FirebaseUser? get() = _currentUser

    private val _isSystemUIVisible = MutableStateFlow(false)
    val isSystemUIVisible: StateFlow<Boolean> get() = _isSystemUIVisible

    private val _appBarTitle = MutableStateFlow("App Title")
    val appBarTitle: StateFlow<String> get() = _appBarTitle

    init { getUserAuth() }

    fun setAppBarTitle(title: String) {
        _appBarTitle.value = title
    }

    private fun getUserAuth() {
        _currentUser = getUserUseCase.getUser()
        _isUserNotRetrieved.value = false
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