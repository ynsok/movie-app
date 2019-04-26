package com.example.movieapp.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.extention.inflate
import com.example.movieapp.models.Movie
import com.example.movieapp.models.Result
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.horizontal_list_item.view.*

typealias CurrentPositionAdapter = () -> Unit
typealias onClickMovie = (Int) -> Unit

class HomeHorizontalRecyclerView : RecyclerView.Adapter<HomeHorizontalRecyclerView.ViewHolder>() {
    private var movieList: Movie? = null
    var clickedMovie: onClickMovie? = null
    var sendCurrentPosition: CurrentPositionAdapter? = null
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = viewGroup.inflate().inflate(R.layout.horizontal_list_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (movieList?.results == null) return 0
        return movieList!!.results.size
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        position: Int
    ) {
        movieList?.results?.get(position)
            ?.let { viewHolder.bindViews(it, sendCurrentPosition, clickedMovie) }
    }

    fun swapData(movieList: Movie) {
        this.movieList = movieList
        notifyDataSetChanged()
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bindViews(movie: Result, lambda: CurrentPositionAdapter?, onClick: onClickMovie?) {
            Picasso.get().load("$URL${movie.poster_path}")
                .into(view.poster_image_id)
            view.movie_title_txt_id.text = movie.title
            lambda?.invoke()
            view.horizontal_cardView_id.setOnClickListener { onClick?.invoke(movie.id) }
        }
    }

    companion object {
        const val URL = "https://image.tmdb.org/t/p/w500"
    }
}