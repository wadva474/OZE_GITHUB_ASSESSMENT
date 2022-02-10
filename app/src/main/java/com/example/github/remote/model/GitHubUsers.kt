package com.example.github.remote.model

import com.example.github.local.model.GithubUser
import com.squareup.moshi.Json

data class GitHubUsers(
    @Json(name = "login")
    val login: String,
    @Json(name = "avatar_url")
    val avatarUrl: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "html_url")
    val htmlUrl: String,
    @Json(name = "followers_url")
    val followersUrl: String
)


fun GitHubUsers.toDomain(): GithubUser {
    return GithubUser(
        login, avatarUrl, url, htmlUrl, followersUrl
    )
}




