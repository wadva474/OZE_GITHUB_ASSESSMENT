package com.example.github.remote.repository

import androidx.paging.PagingData
import com.example.github.local.model.GithubUser
import com.example.github.remote.model.GitHubUsers
import io.reactivex.Flowable


interface GithubUserRepository {
    fun fetchGitHubUsers() : Flowable<PagingData<GithubUser>>
}