package com.develofer.opositate.di

import com.develofer.opositate.feature.calendar.data.local.CalendarDataSource
import com.develofer.opositate.main.data.provider.AbilityDataProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideCalendarDataSource(): CalendarDataSource {
        return CalendarDataSource()
    }

    @Provides
    @Singleton
    fun provideAbilityDataProvider(): AbilityDataProvider = AbilityDataProvider()
}