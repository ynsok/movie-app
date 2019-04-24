package com.example.movieapp.view.model.detail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.movieapp.models.Detail
import com.example.movieapp.models.Video
import com.example.movieapp.network.result.Result
import com.example.movieapp.repositories.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class DetailViewModel(private val repository: Repository) : ViewModel() {

    private val job = Job()
    private val coroutineContext: CoroutineContext get() = job + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    val getMovieDetailSuccess = MutableLiveData<Detail>()
    val getMovieDetailError = MutableLiveData<String>()
    val getMovieDetailException = MutableLiveData<Exception>()

    val getMovieDetailVideoSuccess = MutableLiveData<Video>()
    val getMovieDetailVideoError = MutableLiveData<String>()
    val getMovieDetailVideoException = MutableLiveData<Exception>()

    fun fetchMovieDetail(movieId: Int) {
        scope.launch {
            when (val genreRespond = repository.getMovieDetail(movieId)) {
                is Result.Success -> getMovieDetailSuccess.postValue(genreRespond.data)
                is Result.Error -> getMovieDetailError.postValue(genreRespond.error)
                is Result.Exception -> getMovieDetailException.postValue(genreRespond.exception)
            }
        }
    }

    fun fetchMovieDetailVideo(movieId: Int) {
        scope.launch {
            when (val genreRespond = repository.getMovieDetailVideo(movieId)) {
                is Result.Success -> getMovieDetailVideoSuccess.postValue(genreRespond.data)
                is Result.Error -> getMovieDetailVideoError.postValue(genreRespond.error)
                is Result.Exception -> getMovieDetailVideoException.postValue(genreRespond.exception)
            }
        }
    }

    fun addMovieToFavorite(movie: com.example.movieapp.models.Result) {
        scope.launch {
            repository.addMovieToDataBase(movie)
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    private fun cancelJob() = job.cancel()
}