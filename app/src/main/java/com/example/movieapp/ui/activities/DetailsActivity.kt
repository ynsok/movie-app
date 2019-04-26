package com.example.movieapp.ui.activities

import android.databinding.DataBindingUtil
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import android.widget.Toast
import com.example.movieapp.BR
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityDetailsBinding
import com.example.movieapp.models.Result
import com.example.movieapp.ui.exoplayer.ExoPlayer
import com.example.movieapp.ui.youtubeExtractor.MainYouTubeExtractor
import com.example.movieapp.view.model.detail.DetailViewModel
import kotlinx.android.synthetic.main.activity_details.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

// TODO revenue is often show as 0$ cos its saved as 0 in the database -> if revenue = 0, we should display that it is not known or hide revenue from view

class DetailsActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val detailViewModel: DetailViewModel by instance()
    var isInFavorite: ((Boolean) -> Unit)? = null
    private lateinit var resultMovieObject: Result

    // binding
    private lateinit var binding: ActivityDetailsBinding
    private val exoPlayer: ExoPlayer by instance()
    private val mainYouTubeExtractor: MainYouTubeExtractor by instance()
    private val movieId: Int by lazy { intent.getIntExtra(MOVIE_ID, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        // pass id on click from another fragment
        startFetchingById(movieId)

        isMovieInFavorite(movieId)

        getSuccessRespond()
        setUpDetailsToolbar()
        setUpDetailsCollapsingToolbar()
        initializeFavouritesFabAction()
        exo_player_details_id.player = exoPlayer.getPlayerView()?.player
        getConvertedMovieKey(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
        mainYouTubeExtractor.releaseExtractor()
    }

    private fun setUpDetailsToolbar() {
        setSupportActionBar(movie_details_toolbar_id)
        with(supportActionBar!!) {   setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    private fun setUpDetailsCollapsingToolbar() {
        with(movie_details_collapsing_toolbar_id) {
            setCollapsedTitleTextColor(Color.WHITE)
            setExpandedTitleColor(Color.WHITE)
        }
    }

    private fun isMovieInFavorite(movieId: Int) {
        val liveData = detailViewModel.getAllFavoriteMovies()
        liveData.observe(this, Observer { listOfFavoriteMovies ->
            listOfFavoriteMovies?.forEach {
                if (it.id == movieId) {
                    isInFavorite?.invoke(true)
                    return@Observer
                }
            }
            isInFavorite?.invoke(false)
            return@Observer
        })
    }

    private fun initializeFavouritesFabAction() {
        isInFavorite = { result ->
            if (result) {
                favourites_fab_id.setImageDrawable(ContextCompat.getDrawable(this.applicationContext,R.drawable.ic_star_full_yellow))
            } else {

                favourites_fab_id.setImageDrawable(ContextCompat.getDrawable(this.applicationContext,R.drawable.ic_star_border_yellow))
            }

            favourites_fab_id.setOnClickListener {
                if (result) {
                    detailViewModel.removeFromDatabase(resultMovieObject)
                    favourites_fab_id.setImageDrawable(ContextCompat.getDrawable(this.applicationContext,R.drawable.ic_star_full_yellow))
                    favourites_fab_id.show()
                    Toast.makeText(
                        this,
                        "${resultMovieObject.title} ${getString(R.string.deleted_movie_from_favorite_message)}",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    detailViewModel.addToDatabase(resultMovieObject)
                    favourites_fab_id.setImageDrawable(ContextCompat.getDrawable(this.applicationContext,R.drawable.ic_star_border_yellow))
                    favourites_fab_id.show()

                    Toast.makeText(
                        this,
                        "${resultMovieObject.title} ${getString(R.string.added_movie_to_favorite_message)}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun startFetchingById(id: Int) {
        detailViewModel.fetchMovieDetail(id)
        detailViewModel.fetchMovieDetailVideo(id)
    }

    private fun getSuccessRespond() {
        detailViewModel.getMovieDetailSuccess.observe(this, Observer {
            with(binding) {
                setVariable(BR.movie, it!!)
                executePendingBindings()
            }

            resultMovieObject =
                Result(it!!.id, it.title, it.poster_path, it.vote_average, it.release_date)
        })

        detailViewModel.getMovieDetailVideoSuccess.observe(this, Observer { videoList ->
            val results = videoList?.results
            if (results?.isNotEmpty()!! && results.size >= 0) convertMovieKeyToMp4(results.first().key)
        })
    }

    private fun convertMovieKeyToMp4(movieKey: String) =
        mainYouTubeExtractor.convertMovieKey(movieKey)

    private fun getConvertedMovieKey(bundle: Bundle?) {
        mainYouTubeExtractor.getMovieMp4 = { mp4: String -> startDisplayMovie(mp4,bundle) }
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    private fun startDisplayMovie(movieMp4: String,bundle: Bundle?) {
        exoPlayer.setMovieKey(movieMp4)
        exoPlayer.startPlayMovie()
        bundle?.let { exoPlayer.setPositionOfPlay(it.getLong(CURRENT_POSITION)) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        exoPlayer.getPositionOfMoviePlay()?.let { outState.putLong(CURRENT_POSITION, it) }
    }
    companion object {
        private const val CURRENT_POSITION = "position"
        private const val MOVIE_ID = "movieId"
        fun getIntent(context: Context, movieId: Int): Intent =
            Intent(context, DetailsActivity::class.java).putExtra(MOVIE_ID, movieId)
    }
}