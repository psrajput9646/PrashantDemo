package com.example.stocks.di

import com.example.stocks.data.local.InvestedDao
import com.example.stocks.data.remote.InvestedApi
import com.example.stocks.data.repository.InvestedRepositoryImpl
import com.example.stocks.domain.repository.InvestedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providePostRepository(
        api: InvestedApi,
        dao: InvestedDao
    ): InvestedRepository = InvestedRepositoryImpl(api, dao)

}