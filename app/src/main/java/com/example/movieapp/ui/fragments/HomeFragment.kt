package com.example.movieapp.ui.fragments

import android.os.Bundle
import android.support.constraint.solver.widgets.ConstraintHorizontalLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.repositories.Movie
import com.example.movieapp.ui.activities.GenresActivity
import com.example.movieapp.ui.adapters.HomeRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    lateinit var homeRecyclerAdapter: HomeRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView(home_recommendations_rv_id)
    }

    private fun mockList(): MutableList<Movie> {
            val url = "https://image.tmdb.org/t/p/w500/adw6Lq9FiC9zjYEpOqfq03ituwp.jpg"

        val movie1 = Movie(1L, url)
        val movie2 = Movie(1L, url)
        val movie3 = Movie(1L, url)
        val movie4 = Movie(1L, url)
        val movie5 = Movie(1L, url)
        val movie6 = Movie(1L, url)
        val movie7 = Movie(1L, url)
        val movie8 = Movie(1L, url)
        val movie9 = Movie(1L, url)

        val mockList = mutableListOf(movie1, movie2, movie3, movie4, movie5, movie6, movie7, movie8, movie9)
        return mockList
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        homeRecyclerAdapter = HomeRecyclerViewAdapter()

        GlobalScope.launch(Dispatchers.Main) {
            homeRecyclerAdapter.updateList(mockList())
        }

        recyclerView.adapter = homeRecyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }
}
