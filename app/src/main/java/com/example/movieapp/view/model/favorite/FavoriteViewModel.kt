package com.example.movieapp.view.model.favorite

import android.arch.lifecycle.ViewModel
import com.example.movieapp.models.Result
import com.example.movieapp.repositories.Repository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class FavoriteViewModel(private val repository: Repository) : ViewModel() {
    private val job = Job()
    private val coroutineContext: CoroutineContext get() = job + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    fun getAllFavoriteMovies() = repository.getMovieFavorite()

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    private fun cancelJob() = job.cancel()
}
