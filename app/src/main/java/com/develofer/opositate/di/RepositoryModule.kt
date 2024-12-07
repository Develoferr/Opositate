package com.develofer.opositate.di

import com.develofer.opositate.feature.calendar.data.local.CalendarDataSource
import com.develofer.opositate.feature.calendar.data.repository.CalendarRepositoryImpl
import com.develofer.opositate.feature.calendar.domain.repository.CalendarRepository
import com.develofer.opositate.feature.login.data.AuthRepositoryImpl
import com.develofer.opositate.feature.login.domain.repository.AuthRepository
import com.develofer.opositate.feature.profile.data.repository.UserRepositoryImpl
import com.develofer.opositate.feature.profile.domain.repository.UserRepository
import com.develofer.opositate.feature.settings.data.datasource.SettingsPreferencesDataSource
import com.develofer.opositate.feature.settings.data.repository.SettingsRepositoryImpl
import com.develofer.opositate.feature.settings.domain.repository.SettingsRepository
import com.develofer.opositate.feature.test.data.repository.SolvedTestRepositoryImpl
import com.develofer.opositate.feature.test.data.repository.TestRepositoryImpl
import com.develofer.opositate.feature.test.domain.repository.SolvedTestRepository
import com.develofer.opositate.feature.test.domain.repository.TestRepository
import com.develofer.opositate.main.data.provider.ResourceProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    // Auth Repository
    @Provides
    @Singleton
    fun provideAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository = authRepositoryImpl

    @Provides
    @Singleton
    fun provideAuthRepositoryImpl(
        auth: FirebaseAuth,
        resourceProvider: ResourceProvider
    ): AuthRepositoryImpl = AuthRepositoryImpl(auth, resourceProvider)

    // Calendar Repository
    @Provides
    @Singleton
    fun provideCalendarRepository(
        calendarRepositoryImpl: CalendarRepositoryImpl
    ): CalendarRepository = calendarRepositoryImpl

    @Provides
    @Singleton
    fun provideCalendarRepositoryImpl(
        calendarDataSource: CalendarDataSource
    ): CalendarRepositoryImpl = CalendarRepositoryImpl(calendarDataSource)

    // User Repository
    @Provides
    @Singleton
    fun provideUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository = userRepositoryImpl

    @Provides
    @Singleton
    fun provideUserRepositoryImpl(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore,
        resourceProvider: ResourceProvider
    ): UserRepositoryImpl = UserRepositoryImpl(auth, firestore, resourceProvider)

    // Test Repository
    @Provides
    @Singleton
    fun provideTestRepository(
        testRepositoryImpl: TestRepositoryImpl
    ): TestRepository = testRepositoryImpl

    @Provides
    @Singleton
    fun provideTestRepositoryImpl(
        firestore: FirebaseFirestore,
        resourceProvider: ResourceProvider
    ): TestRepositoryImpl = TestRepositoryImpl(firestore, resourceProvider)

    // Solved Test Repository
    @Provides
    @Singleton
    fun provideSolvedTestRepository(
        solvedTestRepositoryImpl: SolvedTestRepositoryImpl
    ): SolvedTestRepository = solvedTestRepositoryImpl

    @Provides
    @Singleton
    fun provideSolvedTestRepositoryImpl(
        firestore: FirebaseFirestore,
        resourceProvider: ResourceProvider
    ): SolvedTestRepositoryImpl = SolvedTestRepositoryImpl(firestore, resourceProvider)

    // Settings Repository
    @Provides
    @Singleton
    fun provideSettingsRepository(
        settingsRepositoryImpl: SettingsRepositoryImpl
    ): SettingsRepository = settingsRepositoryImpl

    @Provides
    @Singleton
    fun provideSettingsRepositoryImpl(
        settingsPreferencesDataSource: SettingsPreferencesDataSource
    ): SettingsRepositoryImpl = SettingsRepositoryImpl(settingsPreferencesDataSource)
}