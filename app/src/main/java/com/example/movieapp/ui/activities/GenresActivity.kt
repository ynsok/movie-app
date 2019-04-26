package com.example.movieapp.ui.activities

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.movieapp.R
import com.example.movieapp.models.Result
import com.example.movieapp.ui.adapters.GenresRecyclerViewAdapter
import com.example.movieapp.ui.adapters.SpinnerGenres
import com.example.movieapp.view.model.genres.GenresViewModel
import kotlinx.android.synthetic.main.activity_genres.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class GenresActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    lateinit var genresRecyclerViewAdapter: GenresRecyclerViewAdapter
    lateinit var spinnerGenres: SpinnerGenres
    private val genresViewModel: GenresViewModel by instance()
    private var sortedBy = "popularity.desc"
    private var idGenres = 0

    companion object {
        const val ID = "id"

        fun getIntent(context: Context, id: Int): Intent {
            return Intent(context, GenresActivity::class.java).apply {
                putExtra(ID, id)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genres)

        idGenres = intent.getIntExtra(ID, 0)
        if (idGenres != 0) fetchMovieByGenre(sortedBy, idGenres)

        runGenresRecyclerViewAdapter()
        setUpToolbarGenresActivity()
        getMovieByGenreError()
        getMovieByGenreException()
        getMovieByGenreSuccess()
        startSpinner()
        sortBySelected()
        genresRecyclerViewAdapter.putToDetailsId = { it ->
            startDetailsActivity(it)
        }
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    private fun setUpToolbarGenresActivity() {
        setSupportActionBar(toolbar_genres_activity_id)
        with(supportActionBar!!) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
        }
    }

    private fun runGenresRecyclerViewAdapter() {
        recycler_view_genres_id.apply {
            layoutManager =
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    GridLayoutManager(context, 2)
                } else {
                    GridLayoutManager(context, 3)
                }
        }
        genresRecyclerViewAdapter = GenresRecyclerViewAdapter()
        recycler_view_genres_id.adapter = genresRecyclerViewAdapter
        startAnimation()
    }

    private fun getDataFromApiMovies(movieList: List<Result>) {
        genresRecyclerViewAdapter.updateItemList(movieList)
    }

    private fun fetchMovieByGenre(sorted_By: String, genreId: Int) {
        genresViewModel.fetchMovieByGenre(sorted_By, genreId)
    }

    private fun getMovieByGenreSuccess() = genresViewModel.getMovieByGenreSuccess.observe(
        this,
        Observer { genre ->
            getDataFromApiMovies(genre!!.results)
            Log.i("MovieByGenre", "succes")
        })

    private fun getMovieByGenreError() =
        genresViewModel.getMovieByGenreError.observe(
            this,
            Observer { it ->
                Log.i("MovieByGenreError", it)
                Toast.makeText(
                    applicationContext,
                    applicationContext.getString(R.string.no_internet_connection),
                    Toast.LENGTH_LONG
                ).show()
            })

    private fun getMovieByGenreException() = genresViewModel.getMovieByGenreException.observe(
        this,
        Observer { it ->
            Log.i("MovieByGenreException", it?.message)
            Toast.makeText(
                applicationContext,
                applicationContext.getString(R.string.no_internet_connection),
                Toast.LENGTH_LONG
            ).show()
        })

    private fun startSpinner() {
        spinnerGenres = SpinnerGenres(genresRecyclerViewAdapter)
        spinnerGenres.runSpinnerMenu(applicationContext, spinner_genres_id)
    }

    private fun sortBySelected () {
        spinnerGenres.putSortByPosition = { sort ->
            sortedBy = sort
            fetchMovieByGenre(sortedBy, idGenres)
            startAnimation()
        }
    }

    private fun startDetailsActivity(idOfMovie: Int) {
        startActivity(DetailsActivity.getIntent(applicationContext, idOfMovie))
    }

    private fun startAnimation() {
        var anim = AnimationUtils.loadAnimation(applicationContext, R.anim.fly_in_from_top_corner)
        recycler_view_genres_id.animation = anim
        anim.start()
    }
}
