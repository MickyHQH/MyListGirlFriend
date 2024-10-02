package com.haquanghuy.socialdemo.di

import com.haquanghuy.socialdemo.data.repository.UserRepository
import com.haquanghuy.socialdemo.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds abstract fun provideUserRepository(impl: UserRepositoryImpl): UserRepository
}