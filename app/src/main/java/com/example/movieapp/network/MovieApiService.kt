package com.example.movieapp.network

import com.example.movieapp.models.Detail
import com.example.movieapp.models.Genre
import com.example.movieapp.models.Movie
import com.example.movieapp.models.Video
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/popular?")
    fun getPopularMovie(): Deferred<Response<Movie>>

    @GET("movie/top_rated?")
    fun getTopRatedMovie(): Deferred<Response<Movie>>

    @GET("movie/upcoming?")
    fun getUpcomingMovie(): Deferred<Response<Movie>>

    @GET("movie/now_playing?")
    fun getNowPlayingMovie(): Deferred<Response<Movie>>

    @GET("genre/movie/list?")
    fun getGenreList(): Deferred<Response<Genre>>

    @GET("discover/movie?")
    fun getMovieByGenre(@Query("sort_by") sortBy: String, @Query("with_genres") genres: Int): Deferred<Response<Movie>>

    @GET("movie/{id}")
    fun getMovieDetail(@Path("id") movieId: Int): Deferred<Response<Detail>>

    @GET("movie/{id}/videos?")
    fun getMovieDetailVideo(@Path("id") movieId: Int): Deferred<Response<Video>>

    @GET("search/movie?")
    fun getMoviesBySearch(@Query("query") query: String): Deferred<Response<Movie>>
}