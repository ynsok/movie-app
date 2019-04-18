package com.example.movieapp.view.model.Browse

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.movieapp.repositories.Repository

class BrowseViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = BrowseViewModel(repository) as T
}