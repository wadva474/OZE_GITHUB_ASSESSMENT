package com.example.github.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GithubUser(@PrimaryKey val login: String)