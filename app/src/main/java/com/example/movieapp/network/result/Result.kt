package com.example.movieapp.network.result

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val error: String) : Result<Nothing>()
    data class Exception(val exception: kotlin.Exception) : Result<Nothing>()
}