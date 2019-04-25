package com.example.movieapp.ui.exoplayer

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class ExoPlayer (private val context: Context):
    ExoPlayerCallback {
    private var exoPlayerView: PlayerView? = null
    var exoPlayer: SimpleExoPlayer? = null
    private var playbackStateBuilder: PlaybackStateCompat.Builder? = null
    private var mediaSession: MediaSessionCompat? = null


    override fun initialize(mediaUrl: String)  =initializeExoPlayer(mediaUrl)

    override fun release() =releasePlayer()


    private fun initializeExoPlayer(mediaUrl: String) {
        val trackSelector = DefaultTrackSelector()
        exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
        exoPlayerView?.player = exoPlayer

        val userAgent = Util.getUserAgent(context, "Exo")

        val mediaSource = ExtractorMediaSource
            .Factory(DefaultDataSourceFactory(context, userAgent))
            .setExtractorsFactory(DefaultExtractorsFactory())
            .createMediaSource(Uri.parse(mediaUrl))

        exoPlayer?.prepare(mediaSource)
        exoPlayer?.playWhenReady = true

        playbackStateBuilder = PlaybackStateCompat.Builder()

        exoPlayerButtons()
        exoPlayerSize()

        val componentName = ComponentName(context, "Exo")
        mediaSession = MediaSessionCompat(context, "ExoPlayer", componentName, null)
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

}