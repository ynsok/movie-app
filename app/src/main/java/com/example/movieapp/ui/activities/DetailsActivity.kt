package com.example.movieapp.ui.activities

import android.databinding.DataBindingUtil
import android.arch.lifecycle.Observer
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.SparseArray
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.example.movieapp.BR
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityDetailsBinding
import com.example.movieapp.view.model.detail.DetailViewModel
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_details.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

// TODO trailer video doesnt work for some movies ID (e.g. 530,540), works for (500,550)
// TODO revenue is often show as 0$ cos its saved as 0 in the database -> if revenue = 0, we should display that it is not known or hide revenue from view
// TODO Fab button checked or uchecked if the movie is saved in the favourites or not

class DetailsActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val detailViewModel: DetailViewModel by instance()

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, DetailsActivity::class.java)
    }

    // binding
    private lateinit var binding: ActivityDetailsBinding

    private var exoPlayerView: PlayerView? = null
    private var exoPlayer: SimpleExoPlayer? = null
    private var playbackStateBuilder: PlaybackStateCompat.Builder? = null
    private var mediaSession: MediaSessionCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        // pass id on click from another fragment
        startFetchingById(550)

        getSuccessRespond()

        exoPlayerView = findViewById(R.id.exo_player_details_id)

        setUpDetailsToolbar()
        setUpDetailsCollapsingToolbar()
        initializeFavouritesFabAction()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun setUpDetailsToolbar() {
        setSupportActionBar(movie_details_toolbar_id)
        with(supportActionBar!!) {
            setDisplayHomeAsUpEnabled(true)
        }
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

    private fun initializeExoPlayerWithAddress(youtubeKey: String) {
        val youtubeLink = "http://youtube.com/watch?v=$youtubeKey"

        object : YouTubeExtractor(this) {
            public override fun onExtractionComplete(
                ytFiles: SparseArray<YtFile>?,
                vMeta: VideoMeta
            ) {
                if (ytFiles != null) {
                    val itag = 22
                    val downloadUrl = ytFiles.get(itag).url
                    initializeExoPlayer(downloadUrl)
                }
            }
        }.extract(youtubeLink, true, true)
    }

    private fun initializeExoPlayer(mediaUrl: String) {
        val trackSelector = DefaultTrackSelector()
        exoPlayer = ExoPlayerFactory.newSimpleInstance(baseContext, trackSelector)
        exoPlayerView?.player = exoPlayer

        val userAgent = Util.getUserAgent(baseContext, "Exo")

        val mediaSource = ExtractorMediaSource
            .Factory(DefaultDataSourceFactory(baseContext, userAgent))
            .setExtractorsFactory(DefaultExtractorsFactory())
            .createMediaSource(Uri.parse(mediaUrl))

        exoPlayer?.prepare(mediaSource)
        exoPlayer?.playWhenReady = true

        playbackStateBuilder = PlaybackStateCompat.Builder()

        exoPlayerButtons()
        exoPlayerSize()

        val componentName = ComponentName(baseContext, "Exo")
        mediaSession = MediaSessionCompat(baseContext, "ExoPlayer", componentName, null)
        mediaSession?.setPlaybackState(playbackStateBuilder?.build())
    }

    private fun exoPlayerButtons() {
        playbackStateBuilder?.setActions(
            PlaybackStateCompat.ACTION_PLAY or
                    PlaybackStateCompat.ACTION_PAUSE or
                    PlaybackStateCompat.ACTION_FAST_FORWARD
        )
    }

    private fun exoPlayerSize() {
        exoPlayer?.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    }

    private fun releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer?.stop()
            exoPlayer?.release()
            exoPlayer = null
        }
    }

    private fun startFetchingById(id: Int) {
        detailViewModel.fetchMovieDetail(id)
        detailViewModel.fetchMovieDetailVideo(id)
    }

    private fun getSuccessRespond() {
        detailViewModel.getMovieDetailSuccess.observe(
            this,
            Observer {
                with(binding) {
                    setVariable(BR.movie, it!!)
                    executePendingBindings()
                }
            })

        detailViewModel.getMovieDetailVideoSuccess.observe(this, Observer { videoList ->
            if (videoList?.results?.size!! >= 0) {
                initializeExoPlayerWithAddress(videoList.results[0].key)
            }
        })
    }
}