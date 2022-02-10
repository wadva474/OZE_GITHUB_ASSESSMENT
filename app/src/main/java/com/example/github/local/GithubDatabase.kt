package com.example.github.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.github.local.dao.GithubUserDao
import com.example.github.local.model.GithubUser

@Database(
    entities = [GithubUser::class], version = 1, exportSchema = false
)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun userDao() : GithubUserDao
}