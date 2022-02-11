package com.example.github.di

import android.content.Context
import androidx.room.Room
import com.example.github.local.GithubDatabase
import com.example.github.local.dao.GithubUserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Provides
    @Singleton
    fun provideGithubUserDatabase(@ApplicationContext context: Context): GithubDatabase =
        Room.databaseBuilder(
            context,
            GithubDatabase::class.java,
            "github_database.db"
        ).fallbackToDestructiveMigrationOnDowngrade().build()

    @Provides
    fun providesCustomerDao(githubDatabase: GithubDatabase): GithubUserDao =
        githubDatabase.userDao()
}