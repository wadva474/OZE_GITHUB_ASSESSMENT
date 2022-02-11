package com.example.github.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.github.local.model.GithubUser
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface GithubUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGithubUser(githubUser: GithubUser): Completable

    @Query("SELECT * FROM githubuser")
    fun fetchSavedUser(): Flowable<List<GithubUser>>

    @Query("DELETE FROM githubuser WHERE login = :userLogin")
    fun deleteUser(userLogin: String): Completable

    @Query("DELETE FROM githubuser")
    fun deleteAllUser(): Completable

}