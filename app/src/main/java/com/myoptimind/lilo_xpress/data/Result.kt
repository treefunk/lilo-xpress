package com.myoptimind.lilo_xpress.data
import kotlin.Exception

sealed class Result<out T> {
    class Success<out R: Any>(val data: R): Result<R>()
    class Error(val error: Exception): Result<Nothing>()
    object Loading : Result<Nothing>()
}