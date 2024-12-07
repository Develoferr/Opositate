package com.develofer.opositate.feature.settings.data.repository

import com.develofer.opositate.feature.settings.data.datasource.SettingsPreferencesDataSource
import com.develofer.opositate.feature.settings.domain.repository.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val settingsPreferencesDataSource: SettingsPreferencesDataSource
) : SettingsRepository {
    override suspend fun setMondayStartWeek(isMondayStart: Boolean) {
        settingsPreferencesDataSource.saveMondayStartWeek(isMondayStart)
    }

    override fun getMondayStartWeek() =
        settingsPreferencesDataSource.mondayStartWeek

    override suspend fun setAutoThemeSelection(isAutoEnabled: Boolean) {
        settingsPreferencesDataSource.saveAutoThemeSelection(isAutoEnabled)
    }

    override fun getAutoThemeSelection() =
        settingsPreferencesDataSource.autoThemeSelection

    override suspend fun setDarkThemeManual(isDarkTheme: Boolean) {
        settingsPreferencesDataSource.saveDarkThemeManual(isDarkTheme)
    }

    override fun getDarkThemeManual() =
        settingsPreferencesDataSource.darkThemeManual
}