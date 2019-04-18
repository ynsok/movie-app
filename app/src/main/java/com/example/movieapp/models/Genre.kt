package com.example.movieapp.models

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("genres")
    val genres: List<GenreResult>
)

data class GenreResult(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)