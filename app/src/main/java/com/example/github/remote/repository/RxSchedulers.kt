package com.example.github.remote.repository

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RxSchedulers @Inject constructor() {

    fun io() = Schedulers.io()

    fun mainThread() = AndroidSchedulers.mainThread()
}