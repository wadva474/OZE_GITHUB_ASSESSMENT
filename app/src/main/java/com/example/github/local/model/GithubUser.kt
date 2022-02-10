package com.example.github.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GithubUser(
    @PrimaryKey val login: String,
    val avatarUrl: String,
    val url: String,
    val htmlUrl: String,
    val followersUrl: String
)