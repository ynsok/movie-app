package com.example.movieapp.view.model.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.movieapp.models.Movie
import com.example.movieapp.network.result.Result
import com.example.movieapp.repositories.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class HomeViewModel(private val repository: Repository) : ViewModel() {
    private val job = Job()
    private val coroutineContext: CoroutineContext get() = job + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    val getPopularMovieSuccess = MutableLiveData<Movie>()
    val getPopularMovieError = MutableLiveData<String>()
    val getPopularMovieException = MutableLiveData<Exception>()

    val getTopRatedMovieSuccess = MutableLiveData<Movie>()
    val getTopRatedMovieError = MutableLiveData<String>()
    val getTopRatedMovieException = MutableLiveData<Exception>()

    val getNowPlayingMovieSuccess = MutableLiveData<Movie>()
    val getNowPlayingMovieError = MutableLiveData<String>()
    val getNowPlayingMovieException = MutableLiveData<Exception>()

    val getUpcomingMovieSuccess = MutableLiveData<Movie>()
    val getUpcomingMovieError = MutableLiveData<String>()
    val getUpcomingMovieException = MutableLiveData<Exception>()

    init {
        fetchPopularMovie()
        fetchTopRatedMovie()
        fetchNowPlayingMovie()
        fetchUpcomingMovie()
    }

    private fun fetchPopularMovie() {
        scope.launch {
            when (val movieRespond = repository.getPopularMovie()) {
                is Result.Success -> getPopularMovieSuccess.postValue(movieRespond.data)
                is Result.Error -> getPopularMovieError.postValue(movieRespond.error)
                is Result.Exception -> getPopularMovieException.postValue(movieRespond.exception)
            }
        }
    }

    private fun fetchTopRatedMovie() {
        scope.launch {
            when (val movieRespond = repository.getTopRatedMovie()) {
                is Result.Success -> getTopRatedMovieSuccess.postValue(movieRespond.data)
                is Result.Error -> getTopRatedMovieError.postValue(movieRespond.error)
                is Result.Exception -> getTopRatedMovieException.postValue(movieRespond.exception)
            }
        }
    }

    private fun fetchUpcomingMovie() {
        scope.launch {
            when (val movieRespond = repository.getUpcomingMovie()) {
                is Result.Success -> getUpcomingMovieSuccess.postValue(movieRespond.data)
                is Result.Error -> getUpcomingMovieError.postValue(movieRespond.error)
                is Result.Exception -> getUpcomingMovieException.postValue(movieRespond.exception)
            }
        }
    }

    private fun fetchNowPlayingMovie() {
        scope.launch {
            when (val movieRespond = repository.getNowPlayingMovie()) {
                is Result.Success -> getNowPlayingMovieSuccess.postValue(movieRespond.data)
                is Result.Error -> getNowPlayingMovieError.postValue(movieRespond.error)
                is Result.Exception -> getNowPlayingMovieException.postValue(movieRespond.exception)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    private fun cancelJob() = job.cancel()
}