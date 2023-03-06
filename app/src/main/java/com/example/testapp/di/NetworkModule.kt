package com.example.testapp.di

import com.example.testapp.api.RetrofitService
import com.example.testapp.data.RetrofitRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitService() : RetrofitService {
        return RetrofitService.create()
    }
}