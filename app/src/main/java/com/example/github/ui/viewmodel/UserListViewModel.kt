package com.example.github.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.example.github.base.BaseViewModel
import com.example.github.local.model.GithubUser
import com.example.github.remote.repository.GithubUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    githubUserRepository: GithubUserRepository
) : BaseViewModel() {

    private val _githubUsers = MutableLiveData<PagingData<GithubUser>>()
    val githubUser: LiveData<PagingData<GithubUser>> = _githubUsers

    init {
        compositeDisposable.add(
            githubUserRepository.fetchGitHubUsers().cachedIn(viewModelScope).subscribe {
                _githubUsers.value = it
            }
        )
    }


}