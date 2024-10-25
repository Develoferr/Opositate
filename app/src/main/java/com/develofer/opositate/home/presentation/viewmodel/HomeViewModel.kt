package com.develofer.opositate.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.develofer.opositate.login.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    fun logout() {
        logoutUseCase.logout()
    }
}