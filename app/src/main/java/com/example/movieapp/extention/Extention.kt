package com.example.movieapp.extention

import com.example.movieapp.network.result.Result
import retrofit2.Response
import java.lang.Exception

suspend fun <T : Any> safeApi(call: suspend () -> Result<T>): Result<T> =
    try {
        call.invoke()
    } catch (e: Exception) {
        Result.Exception(e)
    }

suspend fun <T : Any> dfsdf(call: suspend () -> Response<Result<T>>): Result<T> {
    try {
        val respond = call.invoke()

        if(respond.isSuccessful) return Result.Success(respond.body())
        else return Result.Error("")
    } catch (e: Exception) {
        return Result.Exception(e)
    }
}
