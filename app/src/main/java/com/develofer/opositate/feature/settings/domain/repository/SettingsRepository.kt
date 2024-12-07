package com.develofer.opositate.feature.settings.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setMondayStartWeek(isMondayStart: Boolean)
    fun getMondayStartWeek(): Flow<Boolean?>

    suspend fun setAutoThemeSelection(isAutoEnabled: Boolean)
    fun getAutoThemeSelection(): Flow<Boolean>

    suspend fun setDarkThemeManual(isDarkTheme: Boolean)
    fun getDarkThemeManual(): Flow<Boolean>
}