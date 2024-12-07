package com.develofer.opositate.feature.settings.domain.usecase

import com.develofer.opositate.feature.settings.domain.repository.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetThemePreferencesUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend fun setAutoThemeSelection(isAutoEnabled: Boolean) {
        settingsRepository.setAutoThemeSelection(isAutoEnabled)
    }

    suspend fun setDarkThemeManual(isDarkTheme: Boolean) {
        settingsRepository.setDarkThemeManual(isDarkTheme)
    }
}