package com.example.movieapp.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.models.GenreResult
import kotlinx.android.synthetic.main.browse_recycler_layout.view.*

class BrowseRecyclerViewAdapter : RecyclerView.Adapter<BrowseViewHolder>() {

    private var items: MutableList<GenreResult> = mutableListOf()
    var putGenreId: ((Int) -> Unit)? = null
    lateinit var myContext: Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): BrowseViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val jokeRow = layoutInflater.inflate(R.layout.browse_recycler_layout, viewGroup, false)
        myContext = viewGroup.context
        return BrowseViewHolder(jokeRow)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BrowseViewHolder, position: Int) {
        holder.itemView.run { card_view_txt_browser_id.text = items[position].name }
        holder.getGenresIcons(items[position].name, holder)
        holder.itemView.card_view_browse_id.setOnClickListener {
            putGenreId?.invoke(items[position].id)
        }
    }

    fun updateItemList(list: List<GenreResult>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}

class BrowseViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun getGenresIcons(items: String, holder: BrowseViewHolder) {
        when (items) {
            "Action" -> changeBackground(holder, R.drawable.action)
            "Adventure" -> changeBackground(holder, R.drawable.adventure)
            "Animation" -> changeBackground(holder, R.drawable.animation)
            "Comedy" -> changeBackground(holder, R.drawable.comedy)
            "Crime" -> changeBackground(holder, R.drawable.crime)
            "Documentary" -> changeBackground(holder, R.drawable.documentary)
            "Drama" -> changeBackground(holder, R.drawable.drama)
            "Family" -> changeBackground(holder, R.drawable.family)
            "Fantasy" -> changeBackground(holder, R.drawable.fantasy)
            "History" -> changeBackground(holder, R.drawable.history)
            "Horror" -> changeBackground(holder, R.drawable.horror)
            "Music" -> changeBackground(holder, R.drawable.music)
            "Mystery" -> changeBackground(holder, R.drawable.mystery)
            "Romance" -> changeBackground(holder, R.drawable.romance)
            "Science Fiction" -> changeBackground(holder, R.drawable.sciencefiction)
            "TV Movie" -> changeBackground(holder, R.drawable.tvmovie)
            "Thriller" -> changeBackground(holder, R.drawable.thriller)
            "War" -> changeBackground(holder, R.drawable.war)
            "Western" -> changeBackground(holder, R.drawable.western)
            else -> changeBackground(holder, R.drawable.other)
        }
    }

    private fun changeBackground(holder: BrowseViewHolder, drawableID: Int) {
        holder.itemView.card_view_browse_background_id.setBackgroundResource(drawableID)
    }
}
