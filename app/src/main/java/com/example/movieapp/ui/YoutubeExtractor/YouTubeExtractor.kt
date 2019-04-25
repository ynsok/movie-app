package com.example.movieapp.ui.YoutubeExtractor

import android.content.Context
import com.commit451.rxyoutubeextractor.RxYouTubeExtractor
import io.reactivex.disposables.CompositeDisposable


class YouTubeExtractor(private val context: Context) {

    private val compositeDisposable = CompositeDisposable()
    private var extractor = RxYouTubeExtractor.create()
}