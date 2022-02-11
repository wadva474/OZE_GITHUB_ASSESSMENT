package com.example.github.networkutils

sealed class LoadingStatus {

    object Success : LoadingStatus()
    data class Error(val errorMessage: String) : LoadingStatus()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success"
            is Error -> "Error[$errorMessage]"
        }
    }
}
