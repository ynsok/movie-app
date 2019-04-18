package com.example.movieapp.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.models.MovieHome
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.movieapp.ui.adapters.MasterRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    lateinit var masterRecyclerViewAdapter: MasterRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupMasterRecyclerView(master_home_rv_id)
        fillInnerRecyclerViews()
    }


    private fun fillInnerRecyclerViews() {
        GlobalScope.launch (Dispatchers.Main){
            masterRecyclerViewAdapter.createdFlag123 = { flag ->
                if (flag) {
                    masterRecyclerViewAdapter.popularInnerRecyclerViewAdapter.swapList(mockList1())
                    masterRecyclerViewAdapter.topRatedInnerRecyclerViewAdapter.swapList(mockList2())
                    masterRecyclerViewAdapter.upcomingInnerRecyclerViewAdapter.swapList(mockList1())
                }
            }

            masterRecyclerViewAdapter.createdFlag4 = { flag ->
                if (flag) {
                    masterRecyclerViewAdapter.nowPlayingInnerRecyclerViewAdapter.swapList(mockList2())
                }
            }
        }
    }

    private fun mockList1(): MutableList<MovieHome> {
        val url = "https://image.tmdb.org/t/p/w500/adw6Lq9FiC9zjYEpOqfq03ituwp.jpg"

        val movie1 = MovieHome(1, "test1", url)
        val movie2 = MovieHome(2, "test2", url)
        val movie3 = MovieHome(3, "test3", url)
        val movie4 = MovieHome(4, "test4", url)
        val movie5 = MovieHome(5, "test5", url)
        val movie6 = MovieHome(6, "test6", url)
        val movie7 = MovieHome(7, "test7", url)
        val movie8 = MovieHome(8, "test8", url)
        val movie9 = MovieHome(9, "test9", url)

        val mockList =
            mutableListOf(movie1, movie2, movie3, movie4, movie5, movie6, movie7, movie8, movie9)
        return mockList
    }

    private fun mockList2(): MutableList<MovieHome> {
        val url = "https://image.tmdb.org/t/p/w500/4VXECCSovzeF3TxpPnKs8L193FS.jpg"

        val movie1 = MovieHome(1, "test1hjudfskd", url)
        val movie2 = MovieHome(2, "tesfdt2", url)
        val movie3 = MovieHome(3, "testaadasda3", url)
        val movie4 = MovieHome(4, "testdsss4", url)
        val movie5 = MovieHome(5, "tegfdsssssssssssssssssst5", url)
        val movie6 = MovieHome(6, "taaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaest6", url)
        val movie7 = MovieHome(7, "testf7", url)
        val movie8 = MovieHome(8, "sssssstest8", url)
        val movie9 = MovieHome(9, "testaa9", url)

        val mockList =
            mutableListOf(movie1, movie2, movie3, movie4, movie5, movie6, movie7, movie8, movie9)
        return mockList
    }

    private fun fillMasterList(): ArrayList<String> {
        val categoryPopular = getString(R.string.category_popular)
        val categoryTopRanked = getString(R.string.category_top_rated)
        val categoryUpcoming = getString(R.string.category_upcoming)
        val categoryNowPlaying = getString(R.string.category_now_playing)
        return arrayListOf(categoryPopular, categoryTopRanked, categoryUpcoming, categoryNowPlaying)
    }

    private fun setupMasterRecyclerView(recyclerView: RecyclerView) {
        masterRecyclerViewAdapter = MasterRecyclerViewAdapter()
        GlobalScope.launch(Dispatchers.Main) {
            masterRecyclerViewAdapter.swapList(fillMasterList())
        }
        recyclerView.adapter = masterRecyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }


}