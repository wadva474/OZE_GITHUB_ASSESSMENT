package com.example.github.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.github.local.model.GithubUser
import com.example.github.remote.model.GitHubUsers
import io.reactivex.Flowable
import javax.inject.Inject

class GithubUserRepositoryImpl @Inject constructor(private val githubUserPagingSource: GithubUserPagingSource) :
    GithubUserRepository {

    override fun fetchGitHubUsers(): Flowable<PagingData<GithubUser>> {
        return Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
            pagingSourceFactory = { githubUserPagingSource }
        ).flowable
    }

}