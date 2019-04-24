package com.example.movieapp.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.movieapp.R
import com.example.movieapp.models.Result
import com.example.movieapp.ui.adapters.MovieRecyclerAdapter
import com.example.movieapp.view.model.favorite.FavoriteViewModel
import com.example.movieapp.view.model.favorite.FavoriteViewModelFactory
import kotlinx.android.synthetic.main.fragment_favourites.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance


class FavouritesFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by kodein()

    private val favoriteViewModelFactory: FavoriteViewModelFactory by instance()

    private lateinit var favoriteViewModel: FavoriteViewModel

    private lateinit var favoriteRecyclerAdapter: MovieRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)
        favoriteViewModel = ViewModelProviders.of(this, favoriteViewModelFactory).get(FavoriteViewModel::class.java)
        //favoriteViewModel.addToDatabase(Result(1,"xxx", "/4VXECCSovzeF3TxpPnKs8L193FS.jpg", 1.0, "30-02-32" ))
        getFavoriteMovies()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupFavoriteRecyclerAdapter(recycler_favorite_id)
        selectedMovie()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun selectedMovie() {
        favoriteRecyclerAdapter.passClickedId = { movieId ->
            Toast.makeText(context, movieId.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun setupFavoriteRecyclerAdapter(recyclerAdapter: RecyclerView) {
        favoriteRecyclerAdapter = MovieRecyclerAdapter()
        with(recyclerAdapter) {
            adapter = favoriteRecyclerAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun getFavoriteMovies() {
        favoriteViewModel.getAllFavoriteMovies().observe(this, Observer {
            favoriteRecyclerAdapter.swapList(it!!)
            Log.i("test", it.toString())
        })
    }
}