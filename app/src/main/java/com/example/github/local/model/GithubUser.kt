package com.example.github.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class GithubUser(
    @PrimaryKey val login: String,
    val avatarUrl: String,
    val url: String,
    val htmlUrl: String,
    val followersUrl: String,
    var isFavourite : Boolean = false
) : Parcelable