package com.example.movieapp.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.models.Result
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_movie_item.view.*

class InnerRecyclerViewAdapter : RecyclerView.Adapter<InnerRecyclerViewAdapter.MyViewHolder>() {

    var listOfMovies = listOf<Result>()

    var selectedMovie: ((Int) -> Unit)? = null

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun fillItem(movie: Result) {
            Picasso.get().load("https://image.tmdb.org/t/p/w500${movie.poster_path}").into(itemView.poster_image_id)
            itemView.movie_title_txt_id.text = movie.title
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.run {
            fillItem(listOfMovies[position])
            itemView.setOnClickListener {
                selectedMovie?.invoke(listOfMovies[position].id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.single_movie_item,
                parent,
                false
            )
        )
    }

    fun swapList(list: List<Result>) {
        listOfMovies = list
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return listOfMovies.size
    }
}