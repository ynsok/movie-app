package com.example.movieapp.ui.exoplayer

interface ExoPlayerCallback {
    fun initialize(mediaUrl: String)
    fun release()
}