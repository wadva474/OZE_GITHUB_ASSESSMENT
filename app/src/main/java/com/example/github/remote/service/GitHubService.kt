package com.example.github.remote.service

import com.example.github.remote.model.GitHubUserResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("users")
    fun fetchUsers(@Query("q") state: String, @Query("page") page: Int): Single<GitHubUserResponse>
}