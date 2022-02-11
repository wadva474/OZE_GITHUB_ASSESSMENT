package com.example.github.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github.networkutils.LoadingStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

     val compositeDisposable = CompositeDisposable()

    val _status = MutableLiveData<LoadingStatus?>()
    val status: MutableLiveData<LoadingStatus?> = _status

    fun errorShown() {
        _status.value = null
    }


    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}