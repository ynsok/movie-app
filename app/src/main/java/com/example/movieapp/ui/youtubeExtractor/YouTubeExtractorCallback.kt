package com.example.movieapp.ui.youtubeExtractor

interface YouTubeExtractorCallback {
    fun convertMovieKey(movieKey: String)
    fun releaseExtractor(): Boolean
}