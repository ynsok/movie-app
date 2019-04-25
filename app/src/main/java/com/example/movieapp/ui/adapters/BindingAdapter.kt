package com.example.movieapp.ui.adapters

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.movieapp.models.ProductionCompany
import com.example.movieapp.models.ProductionCountry
import com.squareup.picasso.Picasso
import java.lang.StringBuilder

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("bindPoster")
    fun bindPoster(imageView: ImageView, posterPath: String?) {
        Picasso.get().load("https://image.tmdb.org/t/p/w500$posterPath").into(imageView)
    }

    @JvmStatic
    @BindingAdapter("bindRevenue")
    fun bindRevenue(textView: TextView, revenue: Int) {
        textView.text = "\$ $revenue"
    }

    @JvmStatic
    @BindingAdapter("bindDoubleToString")
    fun bindDoubleToString(textView: TextView, double: Double) {
        textView.text = double.toString()
    }

    @JvmStatic
    @BindingAdapter("bindTime")
    fun bindTime(textView: TextView, minutes: Int) {

        val hours = minutes / 60
        val minutesLeft = minutes - (hours * 60)

        textView.text = "${hours}h ${minutesLeft}min"
    }

    @JvmStatic
    @BindingAdapter("bindProductionCompany")
    fun bindProductionCompany(textView: TextView, stringsList: List<ProductionCompany>?) {

        val singleString = StringBuilder()
        stringsList?.joinToString(separator = ", ") { productionCompany: ProductionCompany ->
            stringsList?.joinToString(
                separator = ", ",
                prefix = ",",
                truncated = ","
            ) { productionCompany: ProductionCompany ->
                singleString.append(
                    productionCompany.name
                )
            }
        }
        textView.text = singleString
    }

        @JvmStatic
        @BindingAdapter("bindProductionCountry")
        fun bindProductionCountry(textView: TextView, stringsList: List<ProductionCountry>?) {

            val singleString = StringBuilder()
            stringsList?.joinToString(
                separator = ", ",
                prefix = ",",
                truncated = ","
            ) { productionCountry: ProductionCountry ->
                singleString.append(
                    productionCountry.name
                )
            }

            textView.text = singleString
        }
    }
