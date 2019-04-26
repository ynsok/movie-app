package com.example.movieapp.ui.extractor

import android.content.Context
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YtFile
import android.util.SparseArray
import at.huber.youtubeExtractor.YouTubeExtractor

typealias SendExtractedURL = (String) -> Unit

class MainYouTubeExtractor(private val context: Context) : YouTubeExtractorCallback {
    var getMovieMp4: SendExtractedURL? = null
    override fun convertMovieKey(movieKey: String) = startFetchingURL(movieKey)
    private var youTubeExtractor: YouTubeExtractorConverter = YouTubeExtractorConverter(context)
    private fun startFetchingURL(movieKey: String) = youTubeExtractor.extract(movieKey, true, true)
    override fun releaseExtractor() = youTubeExtractor.cancel(true)

    init {
        youTubeExtractor.sendExtractedURL =
            { convertedUrlMovie -> getMovieMp4?.invoke(convertedUrlMovie) }
    }

    class YouTubeExtractorConverter(context: Context) :
        YouTubeExtractor(context) {
        var sendExtractedURL: SendExtractedURL? = null
        private val listOfTag = listOf(
            22,
            43,
            18,
            36,
            17,
            137,
            248,
            136,
            247,
            135,
            244,
            134,
            243,
            133,
            242,
            160,
            278,
            140,
            171,
            249,
            250,
            251
        ).sorted()

        override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, videoMeta: VideoMeta?) {
            if (ytFiles != null) {
                for (i in listOfTag) {
                    val files = ytFiles.get(i)
                    if (files != null) {
                        val getUrl = files.url
                        if (!getUrl.isNullOrEmpty()) {
                            sendExtractedURL?.invoke(getUrl)
                            return
                        }
                    }
                }
            }
        }
    }
}