package com.example.github.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.example.github.base.BaseViewModel
import com.example.github.local.model.GithubUser
import com.example.github.networkutils.LoadingStatus
import com.example.github.remote.repository.GithubUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: GithubUserRepository
) : BaseViewModel() {


    private val _githubUsers = MutableLiveData<List<GithubUser>>()
    val githubUser: LiveData<List<GithubUser>> = _githubUsers


    init {
        getAllUsers()
    }


    private fun getAllUsers() {
        compositeDisposable.add(
            repository.fetchAllUsers().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe {
                    _githubUsers.value = it
            }
        )
    }


    fun deleteAllUser() {
        compositeDisposable.add(
            repository.deleteAllUser().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe {

                }
        )

    }

    fun deleteUser(login: String) {
        compositeDisposable.add(
            repository.deleteUser(login).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe ({
                    _status.value = LoadingStatus.Success
                }, {
                    _status.value = LoadingStatus.Error(it.message.toString())
                })
        )
    }
}