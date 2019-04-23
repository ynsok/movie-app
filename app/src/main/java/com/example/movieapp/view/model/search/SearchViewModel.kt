package com.example.movieapp.view.model.search

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

class SearchViewModel(private val repository: Repository) : ViewModel() {
    private val job = Job()
    private val coroutineContext: CoroutineContext get() = job + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    val getSearchSuccess = MutableLiveData<Movie>()
    val getSearchError = MutableLiveData<String>()
    val getSearchException = MutableLiveData<Exception>()

    fun getMoviesBySearch(searchQuery: String) {
        scope.launch {
            val searchRespond = repository.getMovieBySearch(searchQuery)
            when (searchRespond) {
                is Result.Success -> getSearchSuccess.postValue(searchRespond.data)
                is Result.Error -> getSearchError.postValue(searchRespond.error)
                is Result.Exception -> getSearchException.postValue(searchRespond.exception)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    private fun cancelJob() = job.cancel()
}