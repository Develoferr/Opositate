package com.develofer.opositate.feature.settings.domain.usecase

import com.develofer.opositate.feature.settings.domain.repository.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetThemePreferencesUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    fun getAutoThemeSelection() = settingsRepository.getAutoThemeSelection()
    fun getDarkThemeManual() = settingsRepository.getDarkThemeManual()
}