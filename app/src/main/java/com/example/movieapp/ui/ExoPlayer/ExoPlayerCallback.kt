package com.example.movieapp.ui.ExoPlayer

interface ExoPlayerCallback {
    fun initialize(mediaUrl: String)
    fun release()
}