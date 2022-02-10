package com.example.github.base

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

     val compositeDisposable = CompositeDisposable()


    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}