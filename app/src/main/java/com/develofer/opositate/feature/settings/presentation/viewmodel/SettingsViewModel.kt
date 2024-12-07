package com.develofer.opositate.feature.settings.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develofer.opositate.feature.settings.domain.usecase.GetMondayStartWeekUseCase
import com.develofer.opositate.feature.settings.domain.usecase.GetThemePreferencesUseCase
import com.develofer.opositate.feature.settings.domain.usecase.SetMondayStartWeekUseCase
import com.develofer.opositate.feature.settings.domain.usecase.SetThemePreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val setMondayStartWeekUseCase: SetMondayStartWeekUseCase,
    private val getMondayStartWeekUseCase: GetMondayStartWeekUseCase,
    private val setThemePreferencesUseCase: SetThemePreferencesUseCase,
    private val getThemePreferencesUseCase: GetThemePreferencesUseCase
) : ViewModel() {
    val mondayStartWeek = getMondayStartWeekUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, true)

    val autoThemeSelection = getThemePreferencesUseCase.getAutoThemeSelection()
        .stateIn(viewModelScope, SharingStarted.Lazily, true)

    val darkThemeManual = getThemePreferencesUseCase.getDarkThemeManual()
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    private val _showDeleteAccountDialog = mutableStateOf(false)
    val showDeleteAccountDialog: Boolean
        get() = _showDeleteAccountDialog.value

    fun setShowDeleteAccountDialog(showDialog: Boolean) {
        _showDeleteAccountDialog.value = showDialog
    }

    fun setMondayStartWeek(isMondayStart: Boolean) {
        viewModelScope.launch {
            setMondayStartWeekUseCase(isMondayStart)
        }
    }

    fun setAutoThemeSelection(isAutoEnabled: Boolean) {
        viewModelScope.launch {
            setThemePreferencesUseCase.setAutoThemeSelection(isAutoEnabled)
        }
    }

    fun setDarkThemeManual(isDarkTheme: Boolean) {
        viewModelScope.launch {
            setThemePreferencesUseCase.setDarkThemeManual(isDarkTheme)
        }
    }
}