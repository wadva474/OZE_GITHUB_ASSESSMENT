package com.example.github.remote.repository

import androidx.paging.PagingData
import com.example.github.local.model.GithubUser
import com.example.github.remote.model.GitHubUsers
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


interface GithubUserRepository {
    fun fetchGitHubUsers(): Flowable<PagingData<GithubUser>>
    fun deleteAllUser(): Completable
    fun deleteUser(login: String): Completable
    fun fetchAllUsers(): Flowable<List<GithubUser>>
    fun saveUser(githubUser: GithubUser): Completable
}