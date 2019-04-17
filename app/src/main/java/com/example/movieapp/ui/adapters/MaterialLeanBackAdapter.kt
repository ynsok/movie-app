package com.example.movieapp.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.models.MovieHome
import com.github.florent37.materialleanback.MaterialLeanBack
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_movie_item.view.*

class MaterialLeanBackAdapter: MaterialLeanBack.Adapter<MaterialLeanBackAdapter.MyViewHolder>() {

    var listOfMovies = mutableListOf<MovieHome>()

    //dimension for 2DRecycler
    override fun getLineCount(): Int {
        return 4
    }

    override fun getCellsCount(row: Int): Int {
        return 9
    }

    override fun getTitleForRow(row: Int): String {
        return when (row) {
            0 -> "Recommended"
            1 -> "Popular"
            2 -> "Latest"
            3 -> "in MovieTheater"
            else -> ""
        }
    }
/*
    override fun isCustomView(row: Int): Boolean {
        return super.isCustomView(row)
    }

    override fun getCustomViewForRow(viewGroup: ViewGroup?, row: Int): RecyclerView.ViewHolder {
        return super.getCustomViewForRow(viewGroup, row)
    }
*/
    //TODO: przydzielanie do odpowiednich row

    class MyViewHolder(row: Int, cell: Int, view: View) : MaterialLeanBack.ViewHolder(view) {
        fun fillItem(item: MovieHome) {
            Picasso.get().load(item.imageUrl).into(itemView.poster_image_id)
            itemView.movie_title_txt_id.text = item.title
        }
    }

    override fun onBindViewHolder(myViewHodler: MyViewHolder, position: Int) {
        myViewHodler.run {
            fillItem(listOfMovies[position])
        }
    }

    fun updateList(mutableList: MutableList<MovieHome>) {
        listOfMovies.clear()
        listOfMovies = mutableList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyViewHolder {
        return MyViewHolder(0, 1,
            LayoutInflater.from(parent.context).inflate(
                R.layout.single_movie_item,
                parent,
                false
            )
        )
    }
}