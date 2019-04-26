package com.example.movieapp.view.model.favorite

import android.arch.lifecycle.ViewModel
import com.example.movieapp.repositories.Repository

class FavoriteViewModel(private val repository: Repository) : ViewModel() {
    fun getAllFavoriteMovies() = repository.getMovieFavorite()
}
