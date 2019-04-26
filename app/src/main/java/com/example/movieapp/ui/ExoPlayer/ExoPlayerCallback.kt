package com.example.movieapp.ui.ExoPlayer

import com.google.android.exoplayer2.ui.PlayerView

interface ExoPlayerCallback {
    fun startPlayMovie()
    fun release()
    fun setPlayerState(state: Boolean)
    fun setMovieKey(movieKey: String)
    fun getPlayerView(): PlayerView?
}