package com.example.stocks.di

import com.example.stocks.data.remote.InvestedApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(5000, TimeUnit.SECONDS) // connection timeout
        .readTimeout(5000, TimeUnit.SECONDS)    // server response timeout
        .writeTimeout(5000, TimeUnit.SECONDS)   // request body write timeout
        .build()
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://35dee773a9ec441e9f38d5fc249406ce.api.mockbin.io/")// https://35dee773a9ec441e9f38d5fc249406ce.api.mockbin.io/  https://jsonplaceholder.typicode.com/
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient())
        .build()
    @Provides
    fun providePostApi(retrofit: Retrofit): InvestedApi = retrofit.create(InvestedApi::class.java)
}