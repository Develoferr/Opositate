package com.develofer.opositate.feature.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.feature.login.domain.usecase.LogoutUseCase
import com.develofer.opositate.feature.profile.GetUserNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getUserNameUseCase: GetUserNameUseCase
) : ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> get() = _userName

    init {
        fetchUserName()
    }

    private fun fetchUserName() {
        viewModelScope.launch {
            _userName.value = getUserNameUseCase()
        }
    }
}