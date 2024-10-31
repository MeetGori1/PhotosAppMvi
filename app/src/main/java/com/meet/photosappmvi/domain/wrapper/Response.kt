package com.meet.photosappmvi.domain.wrapper

sealed class Response<out T> {
    data class Success<out T>(val value: T) : Response<T>()
    data class Error(val error: String) : Response<Nothing>()
}
