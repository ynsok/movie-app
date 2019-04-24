package com.example.movieapp.models

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>
)

data class Result(
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val poster_path: String,
    @SerializedName("title")
    val title: String
)