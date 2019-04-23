package com.example.movieapp.repositories

import com.example.movieapp.extention.safeApi
import com.example.movieapp.models.Detail
import com.example.movieapp.models.Genre
import com.example.movieapp.models.Movie
import com.example.movieapp.models.Video
import com.example.movieapp.network.MovieApiService
import com.example.movieapp.network.result.Result

class Repository(private val movieApiService: MovieApiService) {

    suspend fun getPopularMovie(): Result<Movie> =
        safeApi { movieApiService.getPopularMovie().await() }

    suspend fun getTopRatedMovie(): Result<Movie> =
        safeApi { movieApiService.getTopRatedMovie().await() }

    suspend fun getUpcomingMovie(): Result<Movie> =
        safeApi { movieApiService.getUpcomingMovie().await() }

    suspend fun getNowPlayingMovie(): Result<Movie> =
        safeApi { movieApiService.getNowPlayingMovie().await() }

    suspend fun getGenreList(): Result<Genre> =
        safeApi { movieApiService.getGenreList().await() }

    suspend fun getMovieByGenre(sorted_By: String, genreId: Int): Result<Movie> =
        safeApi { movieApiService.getMovieByGenre(sorted_By, genreId).await() }

    suspend fun getMovieDetail(movieId: Int): Result<Detail> =
        safeApi { movieApiService.getMovieDetail(movieId).await() }

    suspend fun getMovieDetailVideo(movieId: Int): Result<Video> =
        safeApi { movieApiService.getMovieDetailVideo(movieId).await() }

    suspend fun getMovieBySearch(searchQuery: String): Result<Movie> =
        safeApi { movieApiService.getMoviesBySearch(searchQuery).await() }
}