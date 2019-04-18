package com.example.movieapp.extention

import com.example.movieapp.network.result.Result
import retrofit2.Response
import java.lang.Exception

suspend fun <T : Any> safeApi(call: suspend () -> Response<T>): Result<T> {
    try {
        val response = call.invoke()
        // HTTP 200
        return if (response.isSuccessful) Result.Success(response.body()!!)
        // HTTP != 200
        else return Result.Error(response.errorBody().toString())
        // Network Exception
    } catch (e: Exception) {
        return Result.Exception(e)
    }
}