package com.example.movieapp.ui.adapters

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import kotlinx.android.synthetic.main.horizontal_recycler_view_layout.view.*
import kotlin.collections.ArrayList

class MasterRecyclerViewAdapter : RecyclerView.Adapter<MasterRecyclerViewAdapter.MyViewHolder>() {

    /*  POPULAR = 1
        TOP_RATED = 2
        UPCOMING = 3
        NOW_PLAYING = 4*/

    lateinit var popularInnerRecyclerViewAdapter: InnerRecyclerViewAdapter
    lateinit var topRatedInnerRecyclerViewAdapter: InnerRecyclerViewAdapter
    lateinit var upcomingInnerRecyclerViewAdapter: InnerRecyclerViewAdapter
    lateinit var nowPlayingInnerRecyclerViewAdapter: InnerRecyclerViewAdapter

    lateinit var context: Context

    private var items = ArrayList<String>()

    var selectedMovie: ((Int) -> Unit)? = null

    override fun getItemCount(): Int {
        return 4
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        context = parent.context
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.horizontal_recycler_view_layout,
                parent,
                false
            )
        )
    }

    fun swapList(list: ArrayList<String>) {
        items.clear()
        items = list
        notifyDataSetChanged()
    }

    var ifCreatedFirstRecycler: ((Boolean) -> Unit)? = null

    var ifCreatedSecondRecycler: ((Boolean) -> Unit)? = null

    var ifCreatedThirdRecycler: ((Boolean) -> Unit)? = null

    var ifCreatedFourthRecycler: ((Boolean) -> Unit)? = null

    /*    var scrollXState = IntArray(4)

    override fun onViewRecycled(holder: MyViewHolder) {
         holder.itemView.scrollX = scrollXState[holder.adapterPosition]
         super.onViewRecycled(holder)
     }*/

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        /*
        // TODO save state
        holder.itemView.inner_home_rv_id.post {
            scrollXState[holder.adapterPosition] = holder.itemView.scrollX
            Log.i("scroll", scrollXState[0].toString())
        }*/
        if (items.isNullOrEmpty()) return
        holder.fillHeader(items, position)

        holder.itemView.inner_home_rv_id.run {
            when (position) {
                0 -> {
                    popularInnerRecyclerViewAdapter = InnerRecyclerViewAdapter()
                    adapter = popularInnerRecyclerViewAdapter
                    layoutManager = LinearLayoutManager(
                        context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    ifCreatedFirstRecycler?.invoke(true)
                    popularInnerRecyclerViewAdapter.selectedMovie = { movieId ->
                        selectedMovie?.invoke(movieId)
                    }
                }
                1 -> {
                    topRatedInnerRecyclerViewAdapter = InnerRecyclerViewAdapter()
                    adapter = topRatedInnerRecyclerViewAdapter
                    layoutManager = LinearLayoutManager(
                        context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    ifCreatedSecondRecycler?.invoke(true)
                    topRatedInnerRecyclerViewAdapter.selectedMovie = { movieId ->
                        selectedMovie?.invoke(movieId)
                    }
                }
                2 -> {
                    upcomingInnerRecyclerViewAdapter = InnerRecyclerViewAdapter()
                    adapter = upcomingInnerRecyclerViewAdapter
                    layoutManager = LinearLayoutManager(
                        context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    ifCreatedThirdRecycler?.invoke(true)
                    upcomingInnerRecyclerViewAdapter.selectedMovie = { movieId ->
                        selectedMovie?.invoke(movieId)
                    }
                }
                3 -> {
                    nowPlayingInnerRecyclerViewAdapter = InnerRecyclerViewAdapter()
                    adapter = nowPlayingInnerRecyclerViewAdapter
                    layoutManager = LinearLayoutManager(
                        context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    ifCreatedFourthRecycler?.invoke(true)
                    nowPlayingInnerRecyclerViewAdapter.selectedMovie = { movieId ->
                        selectedMovie?.invoke(movieId)
                    }
                }
                else -> {
                }
            }
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun fillHeader(item: ArrayList<String>, position: Int) {
            itemView.header_txt_id.text = item[position]
        }
    }
}