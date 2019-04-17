package com.example.movieapp.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.repositories.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_movie_item.view.*

class HomeRecyclerViewAdapter: RecyclerView.Adapter<HomeRecyclerViewAdapter.MyViewHolder>() {

    var listOfMovies = mutableListOf<Movie>()

    lateinit var context: Context

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun fillItem(item: Movie) {
            Picasso.get().load(item.imageUrl).into(itemView.poster_image_id)
        }
    }

    override fun getItemCount(): Int {
        return listOfMovies.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.run {
            fillItem(listOfMovies[p1])
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.single_movie_item,
                parent,
                false
            )
        )
    }

    fun updateList(list: MutableList<Movie>) {
        listOfMovies.clear()
        listOfMovies = list
        notifyDataSetChanged()
    }
}