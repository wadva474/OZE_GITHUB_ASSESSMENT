package com.example.github.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.example.github.base.BaseViewModel
import com.example.github.local.model.GithubUser
import com.example.github.networkutils.LoadingStatus
import com.example.github.remote.repository.GithubUserRepository
import com.example.github.remote.repository.RxSchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val githubUserRepository: GithubUserRepository,
    private val rxSchedulers: RxSchedulers
) : BaseViewModel() {

    private val _githubUsers = MutableLiveData<PagingData<GithubUser>>()
    val githubUser: LiveData<PagingData<GithubUser>> = _githubUsers


    fun fetchGithubUsers(){
        compositeDisposable.add(
            githubUserRepository.fetchGitHubUsers().cachedIn(viewModelScope).subscribe {
                _githubUsers.value = it
            }
        )
    }

    fun saveUser(githubUser: GithubUser) {
        compositeDisposable.add(
            githubUserRepository.saveUser(githubUser)
                .subscribeOn(rxSchedulers.io())
                .observeOn(rxSchedulers.mainThread())
                .subscribe({
                    _status.value = LoadingStatus.Success
                }, {
                    _status.value = LoadingStatus.Error(it.message.toString())
                })
        )
    }
}