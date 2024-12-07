package com.develofer.opositate.feature.settings.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsPreferencesDataSource(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val MONDAY_START_WEEK = booleanPreferencesKey("monday_start_week")
        val AUTO_THEME_SELECTION = booleanPreferencesKey("auto_theme_selection")
        val DARK_THEME_MANUAL = booleanPreferencesKey("dark_theme_manual")
    }

    suspend fun saveMondayStartWeek(isMondayStart: Boolean) {
        dataStore.edit { preferences ->
            preferences[MONDAY_START_WEEK] = isMondayStart
        }
    }

    val mondayStartWeek: Flow<Boolean?> = dataStore.data
        .map { preferences ->
            preferences[MONDAY_START_WEEK]
        }

    suspend fun saveAutoThemeSelection(isAutoEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[AUTO_THEME_SELECTION] = isAutoEnabled
        }
    }

    val autoThemeSelection: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[AUTO_THEME_SELECTION] ?: true
        }

    suspend fun saveDarkThemeManual(isDarkTheme: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_THEME_MANUAL] = isDarkTheme
        }
    }

    val darkThemeManual: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[DARK_THEME_MANUAL] ?: false
        }
}