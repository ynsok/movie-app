package com.example.movieapp.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.extention.inflate
import com.example.movieapp.models.Movie
import com.example.movieapp.models.Result
import kotlinx.android.synthetic.main.search_item_list.view.*

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private val movieList = mutableListOf<Movie>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = viewGroup.inflate().inflate(R.layout.search_item_list, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(movie: Result) {
            view.title_dl_txt_row_search_id.text = movie.title
        }
    }
}