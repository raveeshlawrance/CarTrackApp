package com.cartrack.main.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class ThrowException(val exception: Exception) : Result<Nothing>()
    data class LoginError(val errorMsg: String) : Result<Nothing>()
    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is ThrowException -> "Exception[exception=$exception]"
            is LoginError -> "LoginError[exception=$errorMsg]"
        }
    }
}