package com.example.github.di

import com.example.github.remote.repository.GithubUserRepository
import com.example.github.remote.repository.GithubUserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindGithubUserRepository(githubUserRepositoryImpl: GithubUserRepositoryImpl): GithubUserRepository
}