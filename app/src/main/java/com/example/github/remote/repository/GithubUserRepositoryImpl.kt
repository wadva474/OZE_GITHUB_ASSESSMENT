package com.example.github.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.github.local.dao.GithubUserDao
import com.example.github.local.model.GithubUser
import com.example.github.remote.model.GitHubUsers
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class GithubUserRepositoryImpl @Inject constructor(
    private val githubUserPagingSource: GithubUserPagingSource,
    private val githubUserDao: GithubUserDao
    ) : GithubUserRepository {

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

    override fun deleteAllUser() : Completable {
        return githubUserDao.deleteAllUser()
    }

    override fun deleteUser(login: String) : Completable {
        return githubUserDao.deleteUser(login)
    }

    override fun fetchAllUsers(): Flowable<List<GithubUser>> {
       return githubUserDao.fetchSavedUser()
    }

    override fun saveUser(githubUser: GithubUser) : Completable{
        return githubUserDao.insertGithubUser(githubUser)
    }


}