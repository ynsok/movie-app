package com.example.movieapp.ui.activities

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
import com.example.movieapp.R
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, DetailsActivity::class.java)
    }

    private var exoPlayerView: PlayerView? = null
    private var exoPlayer: SimpleExoPlayer? = null
    private var playbackStateBuilder: PlaybackStateCompat.Builder? = null
    private var mediaSession: MediaSessionCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setUpDetailsToolbar()
        setUpDetailsCollapsingToolbar()

        favouritesFabAction()

        Picasso.get().load("https://image.tmdb.org/t/p/w500/adw6Lq9FiC9zjYEpOqfq03ituwp.jpg")
            .into(movie_image_dl_img_details)

        exoPlayerView = findViewById(R.id.exo_player_details_id)
        // initializeExoPlayer()
        initializeExoPlayerWithAddress("BdJKm16Co6M")

    }

    private fun setUpDetailsToolbar() {
        setSupportActionBar(movie_details_toolbar_id)
        with(supportActionBar!!) {
            setDisplayHomeAsUpEnabled(true)
            title = "Fight Club"                       //movie title on collapsing bar is set here
        }
    }

    private fun setUpDetailsCollapsingToolbar() {
        with(movie_details_collapsing_toolbar_id) {
            setCollapsedTitleTextColor(Color.WHITE)
            setExpandedTitleColor(Color.WHITE)
        }
    }

    private fun favouritesFabAction() {
        var buttonState = 0

        favourites_fab_id.setOnClickListener {
            if (buttonState == 0) {
                //if movie is not in favourites
                favourites_fab_id.setImageDrawable(getDrawable((R.drawable.ic_star_full_yellow)))
                buttonState = 1

            } else {
                //if movie is in favourites already
                favourites_fab_id.setImageDrawable(getDrawable((R.drawable.ic_star_border_yellow)))
                buttonState = 0
            }
        }
    }

    private fun initializeExoPlayerWithAddress(youtubeUrl: String) {
        val youtubeLink = "http://youtube.com/watch?v=$youtubeUrl"

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

        val componentName = ComponentName(baseContext, "Exo")
        mediaSession = MediaSessionCompat(baseContext, "ExoPlayer", componentName, null)

        playbackStateBuilder = PlaybackStateCompat.Builder()

        //Buttons
        playbackStateBuilder?.setActions(
            PlaybackStateCompat.ACTION_PLAY or
            PlaybackStateCompat.ACTION_PAUSE or
            PlaybackStateCompat.ACTION_FAST_FORWARD
        )

        //Scaling
        exoPlayer?.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING

        mediaSession?.setPlaybackState(playbackStateBuilder?.build())
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer?.stop()
            exoPlayer?.release()
            exoPlayer = null
        }
    }
}
