package com.example.movieapp.view.model.browse

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.movieapp.repositories.Repository

@Suppress("UNCHECKED_CAST")
class BrowseViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = BrowseViewModel(repository) as T
}