package com.example.movieapp.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.view.model.home.HomeViewModel
import com.example.movieapp.view.model.home.HomeViewModelFactory
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

    private fun getMoviesSuccess() {
        getPopularMovieSuccess()
        getTopRatedMovieSuccess()
        getUpcomingMovieSuccess()
        getNowPlayingMovieSuccess()
    }

    private fun getMoviesError() {
        getPopularMovieError()
        getTopRatedMovieError()
        getUpcomingMovieError()
        getNowPlayingMovieError()
    }

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
        viewLifecycleOwner,
        Observer { Log.i("PopularSuccess", it?.results?.size.toString()) })

    private fun getPopularMovieError() =
        homeViewModel.getPopularMovieError.observe(viewLifecycleOwner, Observer { Log.i("PopularError", it) })

    private fun getPopularMovieException() = homeViewModel.getPopularMovieException.observe(
        viewLifecycleOwner,
        Observer { Log.i("PopularException", it?.message) })

    /*
    TopRated Movie Respond
    * */
    private fun getTopRatedMovieSuccess() = homeViewModel.getTopRatedMovieSuccess.observe(
        viewLifecycleOwner,
        Observer { Log.i("TopRatedSuccess", it?.results?.size.toString()) })

    private fun getTopRatedMovieError() =
        homeViewModel.getTopRatedMovieError.observe(viewLifecycleOwner, Observer { Log.i("TopRatedError", it) })

    private fun getTopRatedMovieException() = homeViewModel.getTopRatedMovieException.observe(
        viewLifecycleOwner,
        Observer { Log.i("TopRatedException", it?.message) })

    /*
        Upcoming Movie Respond
 * */
    private fun getUpcomingMovieSuccess() = homeViewModel.getUpcomingMovieSuccess.observe(
        viewLifecycleOwner,
        Observer { Log.i("UpcomingSuccess", it?.results?.size.toString()) })

    private fun getUpcomingMovieError() =
        homeViewModel.getUpcomingMovieError.observe(viewLifecycleOwner, Observer { Log.i("UpcomingError", it) })

    private fun getUpcomingMovieException() = homeViewModel.getUpcomingMovieException.observe(
        viewLifecycleOwner,
        Observer { Log.i("UpcomingException", it?.message) })

    /*
         NowPlaying Movie Respond
  * */
    private fun getNowPlayingMovieSuccess() = homeViewModel.getNowPlayingMovieSuccess.observe(
        viewLifecycleOwner,
        Observer { Log.i("NowPlayingSuccess", it?.results?.size.toString()) })

    private fun getNowPlayingMovieError() =
        homeViewModel.getNowPlayingMovieError.observe(
            viewLifecycleOwner,
            Observer { Log.i("NowPlayingError", it) })

    private fun getNowPlayingMovieException() = homeViewModel.getNowPlayingMovieException.observe(
        viewLifecycleOwner,
        Observer { Log.i("NowPlayingException", it?.message) })
}