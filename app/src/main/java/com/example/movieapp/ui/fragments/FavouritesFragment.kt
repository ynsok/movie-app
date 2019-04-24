package com.example.movieapp.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.view.model.favorite.FavoriteViewModel
import com.example.movieapp.view.model.favorite.FavoriteViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance


class FavouritesFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by kodein()

    private val favoriteViewModelFactory: FavoriteViewModelFactory by instance()

    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }
}