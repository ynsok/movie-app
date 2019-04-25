package com.example.movieapp.view.model.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.movieapp.models.Movie
import com.example.movieapp.network.result.Result
import com.example.movieapp.repositories.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(private val repository: Repository) : ViewModel() {
    private val job = Job()
    private val coroutineContext: CoroutineContext get() = job + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    private val getPopularMovieSuccess = MutableLiveData<Movie>()
    private val getPopularMovieError = MutableLiveData<String>()
    private val getPopularMovieException = MutableLiveData<Exception>()

    private val getTopRatedMovieSuccess = MutableLiveData<Movie>()
    private val getTopRatedMovieError = MutableLiveData<String>()
    private val getTopRatedMovieException = MutableLiveData<Exception>()

    private val getNowPlayingMovieSuccess = MutableLiveData<Movie>()
    private val getNowPlayingMovieError = MutableLiveData<String>()
    private val getNowPlayingMovieException = MutableLiveData<Exception>()

    private val getUpcomingMovieSuccess = MutableLiveData<Movie>()
    private val getUpcomingMovieError = MutableLiveData<String>()
    private val getUpcomingMovieException = MutableLiveData<Exception>()

    init {
        startFetchingMovie()
    }

    fun startFetchingMovie() {
        fetchPopularMovie()
        fetchTopRatedMovie()
        fetchNowPlayingMovie()
        fetchUpcomingMovie()
    }

    fun getMoviesError(): LiveData<Result<List<String?>>> {
        val resultError = MediatorLiveData<Result<List<String?>>>()
        resultError.addSource(getPopularMovieError) {
            resultError.value = combineErrorMovieData(
                getPopularMovieError,
                getUpcomingMovieError,
                getNowPlayingMovieError,
                getTopRatedMovieError
            )
        }
        resultError.addSource(getUpcomingMovieError) {
            resultError.value = combineErrorMovieData(
                getPopularMovieError,
                getUpcomingMovieError,
                getNowPlayingMovieError,
                getTopRatedMovieError
            )
        }
        resultError.addSource(getNowPlayingMovieError) {
            resultError.value = combineErrorMovieData(
                getPopularMovieError,
                getUpcomingMovieError,
                getNowPlayingMovieError,
                getTopRatedMovieError
            )
        }
        resultError.addSource(getTopRatedMovieError) {
            resultError.value = combineErrorMovieData(
                getPopularMovieError,
                getUpcomingMovieError,
                getNowPlayingMovieError,
                getTopRatedMovieError
            )
        }

        return resultError
    }

    fun getMoviesException(): LiveData<Result<List<Exception?>>> {
        val resultException = MediatorLiveData<Result<List<Exception?>>>()
        resultException.addSource(getPopularMovieException) {
            resultException.value = combineExceptionMovieData(
                getPopularMovieException,
                getUpcomingMovieException,
                getNowPlayingMovieException,
                getTopRatedMovieException
            )
        }
        resultException.addSource(getUpcomingMovieException) {
            resultException.value = combineExceptionMovieData(
                getPopularMovieException,
                getUpcomingMovieException,
                getNowPlayingMovieException,
                getTopRatedMovieException
            )
        }
        resultException.addSource(getNowPlayingMovieException) {
            resultException.value = combineExceptionMovieData(
                getPopularMovieException,
                getUpcomingMovieException,
                getNowPlayingMovieException,
                getTopRatedMovieException
            )
        }
        resultException.addSource(getTopRatedMovieException) {
            resultException.value = combineExceptionMovieData(
                getPopularMovieException,
                getUpcomingMovieException,
                getNowPlayingMovieException,
                getTopRatedMovieException
            )
        }

        return resultException
    }

    fun getMovies(): LiveData<Result<List<Movie?>>> {
        val result = MediatorLiveData<Result<List<Movie?>>>()
        result.addSource(getPopularMovieSuccess) {
            result.value = combineSuccessMovieData(
                getPopularMovieSuccess,
                getUpcomingMovieSuccess,
                getNowPlayingMovieSuccess,
                getTopRatedMovieSuccess
            )
        }
        result.addSource(getUpcomingMovieSuccess) {
            result.value = combineSuccessMovieData(
                getPopularMovieSuccess,
                getUpcomingMovieSuccess,
                getNowPlayingMovieSuccess,
                getTopRatedMovieSuccess
            )
        }
        result.addSource(getNowPlayingMovieSuccess) {
            result.value = combineSuccessMovieData(
                getPopularMovieSuccess,
                getUpcomingMovieSuccess,
                getNowPlayingMovieSuccess,
                getTopRatedMovieSuccess
            )
        }
        result.addSource(getTopRatedMovieSuccess) {
            result.value = combineSuccessMovieData(
                getPopularMovieSuccess,
                getUpcomingMovieSuccess,
                getNowPlayingMovieSuccess,
                getTopRatedMovieSuccess
            )
        }

        return result
    }

    private fun combineSuccessMovieData(
        _popularMovie: MutableLiveData<Movie>,
        _upcomingMovie: MutableLiveData<Movie>,
        _nowPlayingMovie: MutableLiveData<Movie>,
        _topRatedMovie: MutableLiveData<Movie>
    ): Result<List<Movie?>> {
        val popularMovie: Movie? = _popularMovie.value
        val upcomingMovie = _upcomingMovie.value
        val nowPlayingMovie = _nowPlayingMovie.value
        val topRatedMovie = _topRatedMovie.value
        // Later I will see
        //  if (popularMovie == null || upcomingMovie == null || nowPlayingMovie == null || topRatedMovie == null) return Result.Loading()

        return Result.Success(
            listOf<Movie?>(
                popularMovie,
                upcomingMovie,
                nowPlayingMovie,
                topRatedMovie
            )
        )
    }

    private fun combineErrorMovieData(
        _popularMovie: MutableLiveData<String>,
        _upcomingMovie: MutableLiveData<String>,
        _nowPlayingMovie: MutableLiveData<String>,
        _topRatedMovie: MutableLiveData<String>
    ): Result<List<String?>> {
        val popularMovie: String? = _popularMovie.value
        val upcomingMovie = _upcomingMovie.value
        val nowPlayingMovie = _nowPlayingMovie.value
        val topRatedMovie = _topRatedMovie.value

        return Result.Respond(
            listOf<String?>(
                popularMovie,
                upcomingMovie,
                nowPlayingMovie,
                topRatedMovie
            )
        )
    }

    private fun combineExceptionMovieData(
        _popularMovie: MutableLiveData<Exception>,
        _upcomingMovie: MutableLiveData<Exception>,
        _nowPlayingMovie: MutableLiveData<Exception>,
        _topRatedMovie: MutableLiveData<Exception>
    ): Result<List<Exception?>> {
        val popularMovie: Exception? = _popularMovie.value
        val upcomingMovie = _upcomingMovie.value
        val nowPlayingMovie = _nowPlayingMovie.value
        val topRatedMovie = _topRatedMovie.value

        return Result.Respond(
            listOf<Exception?>(
                popularMovie,
                upcomingMovie,
                nowPlayingMovie,
                topRatedMovie
            )
        )
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
