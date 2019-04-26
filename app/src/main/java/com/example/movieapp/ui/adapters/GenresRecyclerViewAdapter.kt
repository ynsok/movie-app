package com.example.movieapp.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.models.Result
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.genres_recycler_layout.view.*

class GenresRecyclerViewAdapter : RecyclerView.Adapter<GenresViewHolder>() {

    private var items: MutableList<Result> = mutableListOf()
    var putToDetailsId: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): GenresViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val genreRow = layoutInflater.inflate(R.layout.genres_recycler_layout, viewGroup, false)
        return GenresViewHolder(genreRow)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        holder.itemView.run {
            card_view_txt_genres_id.text = items[position].title
            Picasso.get().load("https://image.tmdb.org/t/p/w500${items[position].poster_path}").into(
                card_view_genres_background_id)
        }
        holder.itemView.card_view_genres_id.setOnClickListener {
            putToDetailsId?.invoke(items[position].id)
        }
    }

    fun updateItemList(list: List<Result>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}

class GenresViewHolder(private val view: View) : RecyclerView.ViewHolder(view)
