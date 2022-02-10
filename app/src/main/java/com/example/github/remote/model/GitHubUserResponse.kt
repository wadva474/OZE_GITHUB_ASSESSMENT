package com.example.github.remote.model

import com.squareup.moshi.Json

data class GitHubUserResponse(
    @Json(name = "total_count")
    val totalCount : Int,
    @Json(name = "items")
    val githubUsers : List<GitHubUsers>
)