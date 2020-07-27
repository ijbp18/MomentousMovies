package com.home.momentousmovies.data

sealed class OperationResult<T>(val data: T? = null, val message: String? = null) {
    class Loading<T> : OperationResult<T>()
    class Success<T>(data: T) : OperationResult<T>(data)
    class Error<T>(val throwable: Throwable) : OperationResult<T>(message = throwable.message)
}