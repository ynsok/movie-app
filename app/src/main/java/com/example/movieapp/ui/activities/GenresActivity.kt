package com.example.movieapp.ui.activities

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.movieapp.R
import com.example.movieapp.view.model.Genres.GenresViewModel
import kotlinx.android.synthetic.main.activity_genres.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class GenresActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val genresViewModel: GenresViewModel by instance()
    private val sorted_By = "popularity.desc"
    private val genreId = 28
    // TODO app should go back to 'browse' fragment 'onBackPress' instead of going to 'home' fragment
    // TODO going back to home is set in manifest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genres)

        fetchMovieByGenre()

        getMovieByGenreError()
        getMovieByGenreException()
        getMovieByGenreSuccess()

        setUpToolbarGenresActivity()
    }

    private fun setUpToolbarGenresActivity() {
        setSupportActionBar(toolbar_genres_activity_id)
        // title = getString(R.string.genre_activity_title)

        with(supportActionBar!!) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
        }
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    private fun fetchMovieByGenre() {
        genresViewModel.fetchMovieByGenre(sorted_By, genreId)
    }

    private fun getMovieByGenreSuccess() = genresViewModel.getMovieByGenreSuccess.observe(
        this,
        Observer {
            Log.i("MovieByGenre", "succes") })

    private fun getMovieByGenreError() =
        genresViewModel.getMovieByGenreError.observe(
            this,
            Observer { Log.i("MovieByGenreError", it) })

    private fun getMovieByGenreException() = genresViewModel.getMovieByGenreException.observe(
        this,
        Observer { Log.i("MovieByGenreException", it?.message) })
}