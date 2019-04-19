package com.example.movieapp.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.TokenWatcher
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.movieapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.movieapp.ui.adapters.MasterRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_home.*

import com.example.movieapp.view.model.Home.HomeViewModel
import com.example.movieapp.view.model.Home.HomeViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance
import android.os.Parcelable


class HomeFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by kodein()

    private val homeViewModelFactory: HomeViewModelFactory by instance()

    private val masterRecyclerViewAdapter: MasterRecyclerViewAdapter  by instance()

    private lateinit var homeViewModel: HomeViewModel

    /* val saveState = "state"
    private var mLayoutManagerSavedState: Parcelable? = null
     val saveLayout = "layout"
 */


    //lateinit var masterRecyclerViewAdapter: MasterRecyclerViewAdapter

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

    /*override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if(savedInstanceState != null) {
            val savedRecyclerLayoutState = savedInstanceState.getIntArray(saveState)
            masterRecyclerViewAdapter.scrollXState = savedRecyclerLayoutState
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntArray(saveState, masterRecyclerViewAdapter.scrollXState)
    }
*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupMasterRecyclerView(master_home_rv_id)

        // ------------------------------------------------------------- get id from click -------------------------------------
        masterRecyclerViewAdapter.selectedMovie = { it ->
            Log.i("selected", "selected id: $it")
            Toast.makeText(context, "movie id: $it", Toast.LENGTH_LONG).show()
        }
    }

    private fun fillMasterList(): ArrayList<String> {
        val categoryPopular = getString(R.string.category_popular)
        val categoryTopRanked = getString(R.string.category_top_rated)
        val categoryUpcoming = getString(R.string.category_upcoming)
        val categoryNowPlaying = getString(R.string.category_now_playing)
        return arrayListOf(categoryPopular, categoryTopRanked, categoryUpcoming, categoryNowPlaying)
    }

    private fun setupMasterRecyclerView(recyclerView: RecyclerView) {
        //masterRecyclerViewAdapter = MasterRecyclerViewAdapter()
        masterRecyclerViewAdapter.swapList(fillMasterList())

        recyclerView.adapter = masterRecyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun instantiateHomeViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, homeViewModelFactory).get(HomeViewModel::class.java)
    }

    private fun getMoviesSuccess() {
        getPopularMovieSuccess()
        getTopRatedMovieSuccess()
        getUpcomingMovieSuccess()
        getNowPlayingMovieSuccess()
    }

    private fun getMoviesException() {
        getPopularMovieException()
        getTopRatedMovieException()
        getUpcomingMovieException()
        getNowPlayingMovieException()
    }

    private fun getMoviesError() {
        getPopularMovieError()
        getTopRatedMovieError()
        getUpcomingMovieError()
        getNowPlayingMovieError()
    }

    /*
        Popular Movie Respond
   */
    private fun getPopularMovieSuccess() = homeViewModel.getPopularMovieSuccess.observe(
        this,
        Observer {

            it?.results?.let { movieList ->
                masterRecyclerViewAdapter.popularInnerRecyclerViewAdapter.swapList(
                    movieList
                )
            }

            masterRecyclerViewAdapter.ifCreatedFirstRecycler = { flag ->
                if (flag) {
                    it?.results?.let { movieList ->
                        masterRecyclerViewAdapter.popularInnerRecyclerViewAdapter.swapList(
                            movieList
                        )
                    }
                }
            }

        })

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
        Observer {
            it?.results?.let { movieList ->
                masterRecyclerViewAdapter.topRatedInnerRecyclerViewAdapter.swapList(
                    movieList
                )
                Log.i("TopRatedError", "")
            }
            masterRecyclerViewAdapter.ifCreatedSecondRecycler = { flag ->
                if (flag) {
                    it?.results?.let { movieList ->
                        masterRecyclerViewAdapter.topRatedInnerRecyclerViewAdapter.swapList(
                            movieList
                        )
                    }
                }
            }
        })

    private fun getTopRatedMovieError() =
        homeViewModel.getTopRatedMovieError.observe(
            this,
            Observer { Log.i("TopRatedError", it) })

    private fun getTopRatedMovieException() =
        homeViewModel.getTopRatedMovieException.observe(
            this,
            Observer { Log.i("TopRatedException", it?.message) })


    /*
        Upcoming Movie Respond
 * */
    private fun getUpcomingMovieSuccess() = homeViewModel.getUpcomingMovieSuccess.observe(
        this,
        Observer {
            it?.results?.let { movieList ->
                masterRecyclerViewAdapter.upcomingInnerRecyclerViewAdapter.swapList(
                    movieList
                )
            }
            masterRecyclerViewAdapter.ifCreatedThirdRecycler = { flag ->
                if (flag) {
                    it?.results?.let { movieList ->
                        masterRecyclerViewAdapter.upcomingInnerRecyclerViewAdapter.swapList(
                            movieList
                        )
                    }
                }
            }
        })

    private fun getUpcomingMovieError() =
        homeViewModel.getUpcomingMovieError.observe(
            this,
            Observer { Log.i("UpcomingError", it) })

    private fun getUpcomingMovieException() =
        homeViewModel.getUpcomingMovieException.observe(
            this,
            Observer { Log.i("UpcomingException", it?.message) })

    /*
         NowPlaying Movie Respond
  * */
    private fun getNowPlayingMovieSuccess() =
        homeViewModel.getNowPlayingMovieSuccess.observe(
            this,
            Observer {
                masterRecyclerViewAdapter.ifCreatedFourthRecycler = { flag ->
                    if (flag) {
                        it?.results?.let { movieList ->
                            masterRecyclerViewAdapter.nowPlayingInnerRecyclerViewAdapter.swapList(
                                movieList
                            )
                        }
                    }
                }
            })

    private fun getNowPlayingMovieError() =
        homeViewModel.getNowPlayingMovieError.observe(
            this,
            Observer { Log.i("NowPlayingError", it) })

    private fun getNowPlayingMovieException() =
        homeViewModel.getNowPlayingMovieException.observe(
            this,
            Observer { Log.i("NowPlayingException", it?.message) })
}