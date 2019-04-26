package com.example.movieapp.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.extention.inflate
import com.example.movieapp.models.Result
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_recycler_info.view.*

class MovieRecyclerAdapter : RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder>() {
    private val movieList = mutableListOf<Result>()

    var passClickedId: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = viewGroup.inflate().inflate(R.layout.row_recycler_info, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(rowHolder: ViewHolder, position: Int) {
        rowHolder.run {
            bindView(movieList[position])
        }

        rowHolder.itemView.setOnClickListener {
            passClickedId?.invoke(movieList[position].id)
        }
    }

    fun swapList(resultsList: MutableList<Result>) {
        movieList.clear()
        movieList.addAll(resultsList)
        notifyDataSetChanged()
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(movie: Result) {
            with(view) {
                title_dl_txt_row_search_id.text = movie.title

                if (movie.vote_average != 0.0) {
                    rating_dl_txt_row_search_id.text = movie.vote_average.toString()
                } else {
                    rating_dl_txt_row_search_id.text = view.context.getString(R.string.unrated)
                }

                if (movie.release_date.isNotEmpty()) {
                    production_date_txt_dl_row_search_id.text = movie.release_date
                } else {
                    production_date_txt_dl_row_search_id.text =
                        view.context.getString(R.string.unknown)
                }

                if (movie.poster_path != null) {
                    Picasso.get().load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                        .into(poster_dl_id_row_search)
                }
            }
        }
    }
}
