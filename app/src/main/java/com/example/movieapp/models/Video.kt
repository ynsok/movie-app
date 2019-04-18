package com.example.movieapp.models

import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<VideoResult>
)

data class VideoResult(
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val name: String
)