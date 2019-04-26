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

    private fun genresNames(id: Int) = view.context.getString(id)

    fun getGenresIcons(items: String, holder: BrowseViewHolder) {
        when (items) {
            genresNames(R.string.genres_action) -> changeBackground(holder, R.drawable.action)
            genresNames(R.string.genres_adventure) -> changeBackground(holder, R.drawable.adventure)
            genresNames(R.string.genres_animation) -> changeBackground(holder, R.drawable.animation)
            genresNames(R.string.genres_comedy) -> changeBackground(holder, R.drawable.comedy)
            genresNames(R.string.genres_crime) -> changeBackground(holder, R.drawable.crime)
            genresNames(R.string.genres_documentary) -> changeBackground(holder, R.drawable.documentary)
            genresNames(R.string.genres_drama) -> changeBackground(holder, R.drawable.drama)
            genresNames(R.string.genres_family) -> changeBackground(holder, R.drawable.family)
            genresNames(R.string.genres_fantasy) -> changeBackground(holder, R.drawable.fantasy)
            genresNames(R.string.genres_history) -> changeBackground(holder, R.drawable.history)
            genresNames(R.string.genres_horror) -> changeBackground(holder, R.drawable.horror)
            genresNames(R.string.genres_music) -> changeBackground(holder, R.drawable.music)
            genresNames(R.string.genres_mystery) -> changeBackground(holder, R.drawable.mystery)
            genresNames(R.string.genres_romance) -> changeBackground(holder, R.drawable.romance)
            genresNames(R.string.genres_science_fiction) -> changeBackground(holder, R.drawable.sciencefiction)
            genresNames(R.string.genres_tv_movie) -> changeBackground(holder, R.drawable.tvmovie)
            genresNames(R.string.genres_thriller) -> changeBackground(holder, R.drawable.thriller)
            genresNames(R.string.genres_war) -> changeBackground(holder, R.drawable.war)
            genresNames(R.string.genres_western) -> changeBackground(holder, R.drawable.western)
            else -> changeBackground(holder, R.drawable.other)
        }
    }

    private fun changeBackground(holder: BrowseViewHolder, drawableID: Int) {
        holder.itemView.card_view_browse_background_id.setBackgroundResource(drawableID)
    }
}
