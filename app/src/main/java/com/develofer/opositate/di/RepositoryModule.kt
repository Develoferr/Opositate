package com.develofer.opositate.di

import com.develofer.opositate.feature.calendar.data.local.CalendarDataSource
import com.develofer.opositate.feature.calendar.data.repository.CalendarRepositoryImpl
import com.develofer.opositate.feature.calendar.domain.repository.CalendarRepository
import com.develofer.opositate.feature.login.data.AuthRepositoryImpl
import com.develofer.opositate.feature.login.domain.repository.AuthRepository
import com.develofer.opositate.feature.profile.UserRepository
import com.develofer.opositate.feature.profile.UserRepositoryImpl
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

    @Provides
    @Singleton
    fun provideUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository {
        return userRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideUserRepositoryImpl(
        auth: FirebaseAuth
    ): UserRepositoryImpl {
        return UserRepositoryImpl(auth)
    }

}