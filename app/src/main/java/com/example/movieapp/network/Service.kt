package com.example.movieapp.network
import com.example.movieapp.models.Movie
import com.example.movieapp.network.result.Result

class Service(val movieApiService: MovieApiService) {

    private suspend fun getPopularMovie(): Result<Movie> {
        val respond = movieApiService.getPopularMovie().await()
        if(respond.isSuccessful)return Result.Success(respond.body()!!)
        else return Result.Error("")
    }



}