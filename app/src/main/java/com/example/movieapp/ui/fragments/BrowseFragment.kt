package com.example.movieapp.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.movieapp.R
import com.example.movieapp.view.model.Browse.BrowseViewModel
import com.example.movieapp.view.model.Browse.BrowseViewModelFactory
import kotlinx.android.synthetic.main.fragment_browse.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance

class BrowseFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val browseViewModelFactory: BrowseViewModelFactory by instance()
    private lateinit var browseViewModel: BrowseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instantiateBrowseViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_browse, container, false)
        getGenreListError()
        getGenreListException()
        getGenreListSuccess()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        view.button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.genresActivity))
    }

    private fun getGenreListSuccess() =
        browseViewModel.getGenreListSuccess.observe(
            viewLifecycleOwner,
            Observer { Log.i("BrowserSuccess", it.toString()) })

    private fun getGenreListError() =
        browseViewModel.getGenreListError.observe(viewLifecycleOwner, Observer { Log.i("BrowserError", it) })

    private fun getGenreListException() =
        browseViewModel.getGenreListException.observe(
            viewLifecycleOwner,
            Observer { Log.i("BrowserError", it?.message.toString()) })

    private fun instantiateBrowseViewModel() {
        browseViewModel =
            ViewModelProviders.of(this, browseViewModelFactory).get(BrowseViewModel::class.java)
    }
}
