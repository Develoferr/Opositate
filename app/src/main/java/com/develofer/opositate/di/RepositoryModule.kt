package com.develofer.opositate.di

import com.develofer.opositate.feature.calendar.data.local.CalendarDataSource
import com.develofer.opositate.feature.calendar.data.repository.CalendarRepositoryImpl
import com.develofer.opositate.feature.calendar.domain.repository.CalendarRepository
import com.develofer.opositate.feature.login.data.AuthRepositoryImpl
import com.develofer.opositate.feature.login.domain.repository.AuthRepository
import com.develofer.opositate.feature.profile.UserRepository
import com.develofer.opositate.feature.profile.UserRepositoryImpl
import com.develofer.opositate.feature.test.SolvedTestRepository
import com.develofer.opositate.feature.test.SolvedTestRepositoryImpl
import com.develofer.opositate.feature.test.TestRepository
import com.develofer.opositate.feature.test.TestRepositoryImpl
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
        auth: FirebaseAuth
    ): AuthRepositoryImpl = AuthRepositoryImpl(auth)

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
        firestore: FirebaseFirestore
    ): UserRepositoryImpl = UserRepositoryImpl(auth, firestore)

    // Test Repository
    @Provides
    @Singleton
    fun provideTestRepository(
        testRepositoryImpl: TestRepositoryImpl
    ): TestRepository = testRepositoryImpl

    @Provides
    @Singleton
    fun provideTestRepositoryImpl(
        firestore: FirebaseFirestore
    ): TestRepositoryImpl = TestRepositoryImpl(firestore)

    // Solved Test Repository
    @Provides
    @Singleton
    fun provideSolvedTestRepository(
        solvedTestRepositoryImpl: SolvedTestRepositoryImpl
    ): SolvedTestRepository = solvedTestRepositoryImpl

    @Provides
    @Singleton
    fun provideSolvedTestRepositoryImpl(
        firestore: FirebaseFirestore
    ): SolvedTestRepositoryImpl = SolvedTestRepositoryImpl(firestore)

}