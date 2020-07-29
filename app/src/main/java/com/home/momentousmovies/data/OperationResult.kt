package com.home.momentousmovies.data

import java.lang.Exception

sealed class OperationResult<T>(val data: T? = null, val message: String? = null) {
    class Loading<T> : OperationResult<T>()
    class Success<T>(data: T) : OperationResult<T>(data)
    class Error<T>(val exception: Exception) : OperationResult<T>()
    class CustomError<T>(val errorType: String) : OperationResult<T>()
}