package com.example.movieapp.models

import com.google.gson.annotations.SerializedName

data class Detail(
    @SerializedName("id")
    val id: Int,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val poster_path: String,
    @SerializedName("production_companies")
    val production_companies: List<ProductionCompany>,
    @SerializedName("production_countries")
    val production_countries: List<ProductionCountry>,
    @SerializedName("release_date")
    val release_date: String,
    @SerializedName("revenue")
    val revenue: Int,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val vote_average: Double
)

data class ProductionCompany(
    @SerializedName("name")
    val name: String
)

data class ProductionCountry(
    @SerializedName("name")
    val name: String
)