package com.example.testapp.di

import android.content.Context
import com.example.testapp.data.AppDatabase
import com.example.testapp.data.CharactersDao
import com.example.testapp.data.PlannerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) : AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideCharactersDao(appDatabase: AppDatabase) : CharactersDao {
        return appDatabase.CharactersDao()
    }

    @Provides
    fun providePlannerDao(appDatabase: AppDatabase) = appDatabase.PlannerDao()

    @Provides
    fun provideChildPlannerDao(appDatabase: AppDatabase) = appDatabase.ChildPlannerDao()
}