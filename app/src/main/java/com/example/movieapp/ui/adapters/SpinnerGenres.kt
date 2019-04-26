package com.example.movieapp.ui.adapters

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.movieapp.R

class SpinnerGenres(private val genresRecyclerViewAdapter: GenresRecyclerViewAdapter) {

    lateinit var listOfSortNames: Array<String>
    private var positionOnList = 0
    var putSortByPosition: ((String) -> Unit)? = null

    fun runSpinnerMenu(context: Context, idOfSpinner: Spinner) {

        context.run {
            listOfSortNames = arrayOf(
                getString(R.string.spinner_sort_popularity),
                getString(R.string.spinner_sort_vote_average),
                getString(R.string.spinner_sort_vote_count),
                getString(R.string.spinner_sort_primary_release_date)

            )
        }

        val arrayAdapter = ArrayAdapter(context, R.layout.spinner_genres, listOfSortNames)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1)

        idOfSpinner.adapter = arrayAdapter
        idOfSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                positionOnList = position
                sortByPopularity()
            }
        }
    }

    fun sortByPopularity() {
        val listOfSortPossibilities = arrayOf(
            "popularity.desc",
            "vote_average.desc",
            "vote_count.desc",
            "primary_release_date.desc"

        )
        putSortByPosition?.invoke(listOfSortPossibilities[positionOnList])
    }
}
