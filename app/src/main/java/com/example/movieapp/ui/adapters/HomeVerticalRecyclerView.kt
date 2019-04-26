package com.example.movieapp.ui.adapters

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.movieapp.R
import com.example.movieapp.extention.inflate
import com.example.movieapp.models.Movie

import kotlinx.android.synthetic.main.row_recycler_home.view.*

typealias SavePositions = (Int, Int) -> Unit
typealias SendIdClickedMovie = (Int) -> Unit

class HomeVerticalRecyclerView(
    var horizontalListPosition: MutableList<Int>,
    var header: List<String>
) :
    RecyclerView.Adapter<HomeVerticalRecyclerView.ViewHolder>() {
    private var listOfTypeMovies = emptyList<Movie?>()
    var saveData: SavePositions? = null
    var sendId: SendIdClickedMovie? = null
    var animationMarker: ((Boolean) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = viewGroup.inflate().inflate(R.layout.row_recycler_home, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listOfTypeMovies.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        listOfTypeMovies[position].let { movie ->
            movie?.let { results ->
                viewHolder.getData(
                    results,
                    position,
                    saveData,
                    horizontalListPosition[position],
                    header[position],
                    sendId
                )
            }

            viewHolder.mAdapter.animationMarker = { marker ->
                if (marker) {
                    animationMarker?.invoke(marker)
                    viewHolder.startAnimation()
                }
            }
        }
    }

    fun swapMovie(movie: List<Movie?>) {
        listOfTypeMovies = movie
        notifyDataSetChanged()
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val mAdapter = HomeHorizontalRecyclerView()

        fun getData(
            movie: Movie,
            position: Int,
            lambda: SavePositions?,
            scrollToPosition: Int,
            header: String,
            sendId: SendIdClickedMovie?
        ) {
            initializeRecyclerView(view.inner_home_rv_id, movie)
            scrollToPosition(getLinearLayoutManage(view.inner_home_rv_id), scrollToPosition)
            sendPosition(lambda, position, view.inner_home_rv_id)
            setupHeader(view.header_txt_id, header)
            sendIdMovie(sendId)
        }

        fun startAnimation() {
            itemView.inner_home_rv_id.scheduleLayoutAnimation()
        }

        private fun setupHeader(textView: TextView, header: String) {
            textView.text = header
        }

        private fun initializeRecyclerView(recyclerView: RecyclerView, movie: Movie) {
            recyclerView.layoutManager =
                LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = mAdapter
            recyclerView.setHasFixedSize(true)
            mAdapter.swapData(movieList = movie)
        }

        private fun sendPosition(
            lambda: SavePositions?,
            position: Int,
            recyclerView: RecyclerView
        ) {
            mAdapter.sendCurrentPosition =
                {
                    run {
                        lambda?.invoke(
                            position,
                            getFirstVisibleItem(getLinearLayoutManage(recyclerView))
                        )
                    }
                }
        }

        private fun sendIdMovie(lambda: SendIdClickedMovie?) {
            mAdapter.clickedMovie = { lambda?.invoke(it) }
        }

        private fun getLinearLayoutManage(recyclerView: RecyclerView) =
            recyclerView.layoutManager as LinearLayoutManager

        private fun getFirstVisibleItem(layoutManage: LinearLayoutManager) =
            layoutManage.findFirstVisibleItemPosition()

        private fun scrollToPosition(layoutManage: LinearLayoutManager, position: Int) =
            layoutManage.scrollToPosition(position)
    }
}