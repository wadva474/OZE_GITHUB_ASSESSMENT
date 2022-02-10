package com.example.github.remote.repository

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.example.github.local.model.GithubUser
import com.example.github.remote.model.toDomain
import com.example.github.remote.service.GitHubService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GithubUserPagingSource @Inject constructor(
    private val githubService: GitHubService
) : RxPagingSource<Int, GithubUser>() {
    override fun getRefreshKey(state: PagingState<Int, GithubUser>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey ?: state.closestPageToPosition(
                anchorPosition
            )?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, GithubUser>> {
        val page = params.key ?: 1
        return githubService.fetchUsers("lagos", page)
            .subscribeOn(Schedulers.io()).map {
                LoadResult.Page(
                    data = it.githubUsers.map { githubUser ->
                        githubUser.toDomain()
                    },
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (page == it.totalCount / 15) null else page + 1
                ) as LoadResult<Int, GithubUser>
            }.onErrorReturn {
                LoadResult.Error(it)
            }
    }
}