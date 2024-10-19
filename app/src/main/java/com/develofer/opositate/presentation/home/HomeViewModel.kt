package com.develofer.opositate.presentation.home

import androidx.lifecycle.ViewModel
import com.develofer.opositate.domain.usecase.LogoutUseCase
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