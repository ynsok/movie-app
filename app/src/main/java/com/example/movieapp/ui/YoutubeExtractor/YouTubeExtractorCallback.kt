package com.example.movieapp.ui.YoutubeExtractor

interface YouTubeExtractorCallback {
    fun convertMovieKey(movieKey: String)
    fun releaseExtractor(): Boolean
}