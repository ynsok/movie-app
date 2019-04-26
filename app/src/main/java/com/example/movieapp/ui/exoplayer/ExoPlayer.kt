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
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class ExoPlayer(private val context: Context) :
    ExoPlayerCallback {
    private var mPlayerView: PlayerView? = null
    var mPlayer: SimpleExoPlayer? = null
    private var playbackStateBuilder: PlaybackStateCompat.Builder? = playbackStateCompat()
    private var mediaSession: MediaSessionCompat? = null
    private var movieKey: String = ""

    override fun setPlayerState(state: Boolean) {
        mPlayer?.playWhenReady = state
    }

    override fun setMovieKey(movieKey: String) {
        this.movieKey = movieKey
    }

    override fun startPlayMovie() = playMovie()
    override fun release() = releasePlayer()
    override fun getPlayerView() = mPlayerView
    override fun getPositionOfMoviePlay(): Long? = getCurrentPosition()
    override fun setPositionOfPlay(position: Long) = seekTo(position)

    init {
        mPlayer = instantiatePlayer(context, defaultTrackSelector())
        mPlayerView = getPlayerView(context)
        initializePlayerView(mPlayerView, mPlayer)
        exoPlayerSize()
        mediaSession = mediaSessionCompat(context)
        mediaSession?.setPlaybackState(exoPlayerButtons())
    }

    private fun playMovie() {
        mPlayer?.prepare(extractorMediaSource(context, movieKey))
        mPlayer?.playWhenReady = true
    }

    private fun exoPlayerButtons() =
        playbackStateBuilder?.setActions(
            PlaybackStateCompat.ACTION_PLAY or
                    PlaybackStateCompat.ACTION_PAUSE or
                    PlaybackStateCompat.ACTION_FAST_FORWARD
        )?.build()!!

    private fun exoPlayerSize() {
        mPlayer?.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    }

    // Returns the playback position in the current window, in milliseconds.
    private fun getCurrentPosition() = mPlayer?.currentPosition

    // Seeks to a position specified in milliseconds in the current window.
    private fun seekTo(positionToSeek: Long) = mPlayer?.seekTo(positionToSeek)

    // Constructs an instance that uses a factory to create adaptive track selections.
    private fun defaultTrackSelector() = DefaultTrackSelector(adaptiveTrackSelecto())

    // A bandwidth based adaptive TrackSelection, whose selected track is updated to be the one of highest quality given the current network conditions and the state of the buffer.
    private fun adaptiveTrackSelecto() = AdaptiveTrackSelection.Factory(bandWidthMeter())

    // Creates a bandwidth meter with default parameters.
    private fun bandWidthMeter() = DefaultBandwidthMeter()

    // Create PlayerView
    private fun getPlayerView(context: Context) = PlayerView(context)

    private fun initializePlayerView(playerView: PlayerView?, simpleExoPlayer: SimpleExoPlayer?) {
        // Sets whether the playback controls can be shown. If set to false the playback controls are never visible and are disconnected from the player.
        playerView?.useController = true
        // Call this to try to give focus to a specific view or to one of its descendants
        playerView?.requestFocus()
        // Set the Player to use.
        playerView?.player = simpleExoPlayer
    }

    // Creates a Uri which parses the given encoded URI string
    private fun getVideoUri(key: String) = Uri.parse(key)

    // A component from which streams of data can be read.
    private fun dataSourceFactory(context: Context) =
        DefaultDataSourceFactory(context, getUserAgent(context, USER_AGENT_NAME))

    // Returns a user agent string based on the given application name and the library version
    private fun getUserAgent(context: Context, userName: String) =
        Util.getUserAgent(context, userName)

    // Provides one period that loads data from a Uri and extracted using an Extractor.
    // If the possible input stream container formats are known, pass a factory that instantiates extractors for them to the constructor.
    // Returns a new ExtractorMediaSource using the current parameters.
    private fun extractorMediaSource(context: Context, movieKey: String) =
        ExtractorMediaSource.Factory(dataSourceFactory(context)).setExtractorsFactory(
            defauldExtractorFactory()
        ).createMediaSource(getVideoUri(movieKey))

    private fun defauldExtractorFactory() = DefaultExtractorsFactory()
    private fun instantiatePlayer(context: Context, trackSelector: DefaultTrackSelector) =
        ExoPlayerFactory.newSimpleInstance(context, trackSelector)

    private fun playbackStateCompat() = PlaybackStateCompat.Builder()
    // Allows interaction with media controllers, volume keys, media buttons, and transport controls.
    private fun mediaSessionCompat(context: Context) =
        MediaSessionCompat(context, TAG, componentName(context), null)

    // Create a new component identifier from a Context and class name.
    private fun componentName(context: Context) = ComponentName(context, CLS_NAME)

    private fun releasePlayer() {
        if (mPlayer != null) {
            mPlayer?.stop()
            mPlayer?.release()
            mPlayer = null
        }
        if (mediaSession != null) mediaSession?.release()
    }

    companion object {
        private const val USER_AGENT_NAME = "movie_app"
        // cls – The name of the class inside of pkg(A Context for the package implementing the component) that implements the component.
        private const val CLS_NAME = "Exo"
        // A short name for debugging purposes.
        private const val TAG = "Tag"
    }
}
