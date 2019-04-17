package com.example.movieapp.ui.fragments

import android.graphics.Typeface
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.models.MovieHome
import com.example.movieapp.ui.adapters.MaterialLeanBackAdapter
import com.github.florent37.materialleanback.MaterialLeanBack
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.widget.Toast


import com.example.movieapp.view.model.Home.HomeViewModel
import com.example.movieapp.view.model.Home.HomeViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance

class HomeFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val homeViewModelFactory: HomeViewModelFactory by instance()
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instantiateHomeViewModel()
    }

    lateinit var homeRecyclerAdapter: HomeRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        getMoviesError()
        getMoviesException()
        getMoviesSuccess()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView(home_recommendations_rv_id)
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
    private fun getMoviesSuccess() {
        getPopularMovieSuccess()
        getTopRatedMovieSuccess()
        getUpcomingMovieSuccess()
        getNowPlayingMovieSuccess()
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

    private fun setupRecyclerView(materialLeanBackId: MaterialLeanBack) {
        materialLeanBackAdapter = MaterialLeanBackAdapter()
    private fun setupRecyclerView(recyclerView: RecyclerView) {
        homeRecyclerAdapter = HomeRecyclerViewAdapter()
    private fun getMoviesError() {
        getPopularMovieError()
        getTopRatedMovieError()
        getUpcomingMovieError()
        getNowPlayingMovieError()
    }

        GlobalScope.launch(Dispatchers.Main) {
            homeRecyclerAdapter.updateList(mockList())
        }

        recyclerView.adapter = homeRecyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    private fun getMoviesException() {
        getPopularMovieException()
        getTopRatedMovieException()
        getUpcomingMovieException()
        getNowPlayingMovieException()
    }

    private fun instantiateHomeViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, homeViewModelFactory).get(HomeViewModel::class.java)
    }

    /*
              Popular Movie Respond
  * */
    private fun getPopularMovieSuccess() = homeViewModel.getPopularMovieSuccess.observe(
        this,
        Observer { Log.i("PopularSuccess", it?.results?.size.toString()) })

    private fun getPopularMovieError() =
        homeViewModel.getPopularMovieError.observe(this, Observer { Log.i("PopularError", it) })

    private fun getPopularMovieException() = homeViewModel.getPopularMovieException.observe(
        this,
        Observer { Log.i("PopularException", it?.message) })

    /*
    TopRated Movie Respond
    * */
    private fun getTopRatedMovieSuccess() = homeViewModel.getTopRatedMovieSuccess.observe(
        this,
        Observer { Log.i("TopRatedSuccess", it?.results?.size.toString()) })

    private fun getTopRatedMovieError() =
        homeViewModel.getTopRatedMovieError.observe(this, Observer { Log.i("TopRatedError", it) })

    private fun getTopRatedMovieException() = homeViewModel.getTopRatedMovieException.observe(
        this,
        Observer { Log.i("TopRatedException", it?.message) })

    /*
        Upcoming Movie Respond
 * */
    private fun getUpcomingMovieSuccess() = homeViewModel.getUpcomingMovieSuccess.observe(
        this,
        Observer { Log.i("UpcomingSuccess", it?.results?.size.toString()) })

    private fun getUpcomingMovieError() =
        homeViewModel.getUpcomingMovieError.observe(this, Observer { Log.i("UpcomingError", it) })

    private fun getUpcomingMovieException() = homeViewModel.getUpcomingMovieException.observe(
        this,
        Observer { Log.i("UpcomingException", it?.message) })

    /*
         NowPlaying Movie Respond
  * */
    private fun getNowPlayingMovieSuccess() = homeViewModel.getNowPlayingMovieSuccess.observe(
        this,
        Observer { Log.i("NowPlayingSuccess", it?.results?.size.toString()) })

    private fun getNowPlayingMovieError() =
        homeViewModel.getNowPlayingMovieError.observe(
            this,
            Observer { Log.i("NowPlayingError", it) })

    private fun getNowPlayingMovieException() = homeViewModel.getNowPlayingMovieException.observe(
        this,
        Observer { Log.i("NowPlayingException", it?.message) })
}