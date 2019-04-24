package com.example.movieapp.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>
)
@Entity
data class Result(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("poster_path")
    val poster_path: String?,
    @SerializedName("vote_average")
    val vote_average: Double,
    @SerializedName("release_date")
    val release_date: String
)