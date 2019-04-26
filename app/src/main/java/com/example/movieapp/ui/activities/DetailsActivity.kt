package com.example.movieapp.ui.activities

import android.databinding.DataBindingUtil
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.movieapp.BR
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityDetailsBinding
import com.example.movieapp.ui.ExoPlayer.ExoPlayer
import com.example.movieapp.ui.YoutubeExtractor.MainYouTubeExtractor
import com.example.movieapp.view.model.detail.DetailViewModel
import kotlinx.android.synthetic.main.activity_details.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class DetailsActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val detailViewModel: DetailViewModel by instance()
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
        getSuccessRespond()
        setUpDetailsToolbar()
        setUpDetailsCollapsingToolbar()
        initializeFavouritesFabAction()
        exo_player_details_id.player = exoPlayer.getPlayerView()?.player
        getConvertedMovieKey()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
        mainYouTubeExtractor.releaseExtractor()
    }

    private fun setUpDetailsToolbar() {
        setSupportActionBar(movie_details_toolbar_id)
        with(supportActionBar!!) { setDisplayHomeAsUpEnabled(true) }
    }

    private fun setUpDetailsCollapsingToolbar() {
        with(movie_details_collapsing_toolbar_id) {
            setCollapsedTitleTextColor(Color.WHITE)
            setExpandedTitleColor(Color.WHITE)
        }
    }

    private fun initializeFavouritesFabAction() {
        var buttonState = 0

        favourites_fab_id.setOnClickListener {
            if (buttonState == 0) {
                // if movie is not in favourites
                favourites_fab_id.setImageDrawable(getDrawable((R.drawable.ic_star_full_yellow)))
                buttonState = 1
            } else {
                // if movie is in favourites already
                favourites_fab_id.setImageDrawable(getDrawable((R.drawable.ic_star_border_yellow)))
                buttonState = 0
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
        })

        detailViewModel.getMovieDetailVideoSuccess.observe(this, Observer { videoList ->
            val results = videoList?.results
            if (results?.isNotEmpty()!! && results.size >= 0) convertMovieKeyToMp4(results.first().key)
        })
    }

    private fun convertMovieKeyToMp4(movieKey: String) =
        mainYouTubeExtractor.convertMovieKey(movieKey)

    private fun getConvertedMovieKey() {
        mainYouTubeExtractor.getMovieMp4 = { mp4: String -> startDisplayMovie(mp4) }
    }

    private fun startDisplayMovie(movieMp4: String) {
        exoPlayer.setMovieKey(movieMp4)
        exoPlayer.startPlayMovie()
    }

    companion object {
        private const val MOVIE_ID = "movieId"
        fun getIntent(context: Context, movieId: Int): Intent =
            Intent(context, DetailsActivity::class.java).putExtra(MOVIE_ID, movieId)
    }
}