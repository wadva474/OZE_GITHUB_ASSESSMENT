package com.example.github.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.github.local.model.GithubUser

@Dao
interface GithubUserDao {

    @Insert
    fun insertGithubUser(githubUser : GithubUser)

    @Query("DELETE FROM githubuser WHERE login = :userLogin")
    fun deleteUser(userLogin : String)

    @Query("DELETE FROM githubuser")
    fun deleteAllUser()

}