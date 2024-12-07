package com.develofer.opositate.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.develofer.opositate.feature.settings.data.datasource.SettingsPreferencesDataSource
import com.develofer.opositate.main.extensions.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideSettingsPreferencesDataSource(dataStore: DataStore<Preferences>): SettingsPreferencesDataSource {
        return SettingsPreferencesDataSource(dataStore)
    }
}