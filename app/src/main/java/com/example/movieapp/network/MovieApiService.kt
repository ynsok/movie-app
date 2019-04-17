package com.example.movieapp.network

import com.example.movieapp.models.Movie
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface MovieApiService {


    @GET("top_rated?")
    fun getPopularMovie():Deferred<Response<Movie>>
}
/*
* https://api.themoviedb.org/3/movie/popular?api_key=43b0beac92cd82bff7b37d8530e34de8&language=en-US&page=2*/