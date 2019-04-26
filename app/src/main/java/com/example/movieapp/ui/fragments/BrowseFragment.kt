package com.example.movieapp.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.movieapp.R
import com.example.movieapp.models.GenreResult
import com.example.movieapp.ui.activities.GenresActivity
import com.example.movieapp.ui.adapters.BrowseRecyclerViewAdapter
import com.example.movieapp.view.model.browse.BrowseViewModel
import com.example.movieapp.view.model.browse.BrowseViewModelFactory
import kotlinx.android.synthetic.main.activity_genres.*
import kotlinx.android.synthetic.main.fragment_browse.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance

class BrowseFragment : Fragment(), KodeinAware {
    lateinit var browseRecyclerViewAdapter: BrowseRecyclerViewAdapter
    override val kodein by kodein()
    private val browseViewModelFactory: BrowseViewModelFactory by instance()
    private lateinit var browseViewModel: BrowseViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_browse, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        runBrowseRecyclerViewAdapter()
        instantiateBrowseViewModel()
        getGenreListSuccess()
        getGenreListError()
        getGenreListException()
        browseRecyclerViewAdapter.putGenreId = { it: Int ->
            startGenresActivity(it)
        }
    }

    private fun runBrowseRecyclerViewAdapter() {
        recycler_view_browse_id.apply {
            layoutManager =
                GridLayoutManager(activity, 3)
        }
        browseRecyclerViewAdapter = BrowseRecyclerViewAdapter()
        recycler_view_browse_id.adapter = browseRecyclerViewAdapter
        startAnimation()
    }

    private fun getGenreListSuccess() =
        browseViewModel.getGenreListSuccess.observe(
            this,
            Observer { genre -> getDataFromApi(genre!!.genres) })

    private fun getGenreListError() =
        browseViewModel.getGenreListError.observe(this, Observer {
            Toast.makeText(
                context,
                context?.getString(R.string.no_internet_connection),
                Toast.LENGTH_LONG
            ).show()
            Log.i("BrowserError", it)
        })

    private fun getGenreListException() =
        browseViewModel.getGenreListException.observe(
            this,
            Observer {
                Log.i("BrowserError", it?.message.toString())
                Toast.makeText(
                    context,
                    context?.getString(R.string.no_internet_connection),
                    Toast.LENGTH_LONG
                ).show()
            })

    private fun instantiateBrowseViewModel() {
        browseViewModel =
            ViewModelProviders.of(this, browseViewModelFactory).get(BrowseViewModel::class.java)
    }

    private fun getDataFromApi(genreList: List<GenreResult>) {
        browseRecyclerViewAdapter.updateItemList(genreList)
    }

    private fun startGenresActivity(idOfGenres: Int) {
        startActivity(GenresActivity.getIntent(context!!, idOfGenres))
    }

    private fun startAnimation() {
        var anim = AnimationUtils.loadAnimation(context, R.anim.fly_in_from_center)
        recycler_view_browse_id.animation = anim
        anim.start()
    }
}
