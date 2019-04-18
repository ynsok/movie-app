package com.example.movieapp.ui.adapters

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

    var createdFlag123: ((Boolean) -> Unit)? = null

    var createdFlag4: ((Boolean) -> Unit)? = null

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.fillHeader(items, position)
        when (position) {
            0 -> {
                popularInnerRecyclerViewAdapter = InnerRecyclerViewAdapter()
                holder.itemView.inner_home_rv_id.adapter = popularInnerRecyclerViewAdapter
                holder.itemView.inner_home_rv_id.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            }
            1 -> {
                topRatedInnerRecyclerViewAdapter = InnerRecyclerViewAdapter()
                holder.itemView.inner_home_rv_id.adapter = topRatedInnerRecyclerViewAdapter
                holder.itemView.inner_home_rv_id.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            }
            2 -> {
                upcomingInnerRecyclerViewAdapter = InnerRecyclerViewAdapter()
                holder.itemView.inner_home_rv_id.adapter = upcomingInnerRecyclerViewAdapter
                holder.itemView.inner_home_rv_id.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                createdFlag123?.invoke(true)
            }
            3 -> {
                nowPlayingInnerRecyclerViewAdapter = InnerRecyclerViewAdapter()
                holder.itemView.inner_home_rv_id.adapter = nowPlayingInnerRecyclerViewAdapter
                holder.itemView.inner_home_rv_id.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                createdFlag4?.invoke(true)
            }
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun fillHeader(item: ArrayList<String>, position: Int){
            itemView.header_txt_id.text = item[position]
        }
    }
}