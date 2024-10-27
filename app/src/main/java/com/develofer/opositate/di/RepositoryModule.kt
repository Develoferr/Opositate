package com.develofer.opositate.di

import com.develofer.opositate.features.calendar.data.local.CalendarDataSource
import com.develofer.opositate.features.calendar.data.repository.CalendarRepositoryImpl
import com.develofer.opositate.features.calendar.domain.repository.CalendarRepository
import com.develofer.opositate.features.login.data.AuthRepositoryImpl
import com.develofer.opositate.features.login.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository {
        return authRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideAuthRepositoryImpl(
        auth: FirebaseAuth
    ): AuthRepositoryImpl {
        return AuthRepositoryImpl(auth)
    }

    @Provides
    @Singleton
    fun provideCalendarRepository(
        calendarRepositoryImpl: CalendarRepositoryImpl
    ): CalendarRepository {
        return calendarRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideCalendarRepositoryImpl(
        calendarDataSource: CalendarDataSource
    ): CalendarRepositoryImpl {
        return CalendarRepositoryImpl(calendarDataSource)
    }
}