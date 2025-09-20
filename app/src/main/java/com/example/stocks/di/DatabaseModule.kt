package com.example.stocks.di

import android.content.Context
import androidx.room.Room
import com.example.stocks.data.local.AppDatabase
import com.example.stocks.data.local.InvestedDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()

    @Provides
    fun providePostDao(db: AppDatabase): InvestedDao = db.investDao()
}