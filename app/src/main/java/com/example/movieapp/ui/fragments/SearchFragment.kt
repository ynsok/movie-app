package com.example.movieapp.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.view.model.search.SearchViewModel
import com.example.movieapp.view.model.search.SearchViewModelFactory
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_search.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val searchViewModelFactory: SearchViewModelFactory by instance()
    private lateinit var searchViewModel: SearchViewModel
    private val compositeDisposable = CompositeDisposable()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        startSearching(view)
        getSearchError()
        getSearchSuccess()
        getSearchException()

        /*//TODO show keyboard and focus on search when entering the fragment, close keyboard after leaving it
        search_txt_id.requestFocus()
        val inputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        */
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instantiateSearchViewModel()
    }

    private fun startSearching(view: View) {
        val disposable = Observable.create(ObservableOnSubscribe<String> { subscriber ->
            view.search_txt_id.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    subscriber.onNext(s.toString())
                }
            })
        }).debounce(250, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .filter { text: String -> text.isNotBlank() }
            .subscribe { searchViewModel.getMoviesBySearch(it) }

        compositeDisposable.addAll(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun instantiateSearchViewModel() {
        searchViewModel =
            ViewModelProviders.of(this, searchViewModelFactory).get(SearchViewModel::class.java)
    }

    private fun getSearchSuccess() =
        searchViewModel.getSearchSuccess.observe(
            viewLifecycleOwner,
            Observer { Log.i("SearchSuccess", it.toString()) })

    private fun getSearchError() =
        searchViewModel.getSearchError.observe(
            viewLifecycleOwner,
            Observer { Log.i("SearchError", it) })

    private fun getSearchException() =
        searchViewModel.getSearchException.observe(
            viewLifecycleOwner,
            Observer { Log.i("SearchError", it?.message.toString()) })
}