package com.develofer.opositate.di

import com.develofer.opositate.data.datarepository.AuthRepositoryImpl
import com.develofer.opositate.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

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
}