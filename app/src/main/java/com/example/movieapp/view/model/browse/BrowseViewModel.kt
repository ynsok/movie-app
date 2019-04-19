package com.example.movieapp.view.model.browse

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.movieapp.models.Genre
import com.example.movieapp.network.result.Result
import com.example.movieapp.repositories.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class BrowseViewModel(private val repository: Repository) : ViewModel() {
    private val job = Job()
    private val coroutineContext: CoroutineContext get() = job + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    val getGenreListSuccess = MutableLiveData<Genre>()
    val getGenreListError = MutableLiveData<String>()
    val getGenreListException = MutableLiveData<Exception>()

    init {
        fetchGenreList()
    }

    private fun fetchGenreList() {
        scope.launch {
            when (val genreRespond = repository.getGenreList()) {
                is Result.Success -> getGenreListSuccess.postValue(genreRespond.data)
                is Result.Error -> getGenreListError.postValue(genreRespond.error)
                is Result.Exception -> getGenreListException.postValue(genreRespond.exception)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    private fun cancelJob() = job.cancel()
}