package com.example.movieapp.view.model.Genres

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

class GenresViewModel(private val repository: Repository) : ViewModel() {

    private val job = Job()
    private val coroutineContext: CoroutineContext get() = job + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    val getMovieByGenreSuccess = MutableLiveData<Movie>()
    val getMovieByGenreError = MutableLiveData<String>()
    val getMovieByGenreException = MutableLiveData<Exception>()

    fun fetchMovieByGenre(sortedBy: String, genreId: Int) {
        scope.launch {
            when (val genreRespond = repository.getMovieByGenre(sortedBy, genreId)) {
                is Result.Success -> getMovieByGenreSuccess.postValue(genreRespond.data)
                is Result.Error -> getMovieByGenreError.postValue(genreRespond.error)
                is Result.Exception -> getMovieByGenreException.postValue(genreRespond.exception)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    private fun cancelJob() = job.cancel()
}