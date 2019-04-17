package com.example.movieapp.ui.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.models.MovieHome
import com.example.movieapp.ui.adapters.MaterialLeanBackAdapter
import com.github.florent37.materialleanback.MaterialLeanBack
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.widget.Toast



class HomeFragment : Fragment() {

    lateinit var materialLeanBackAdapter: MaterialLeanBackAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView(material_lean_back_id)

        material_lean_back_id.setOnItemClickListener(object : MaterialLeanBack.OnItemClickListener {
            override fun onTitleClicked(row: Int, text: String) {
                Toast.makeText(
                    context,
                    "onTitleClicked $row $text",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onItemClicked(row: Int, column: Int) {
                Toast.makeText(
                    context,
                    "onItemClicked $row $column",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
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

    private fun setupRecyclerView(materialLeanBackId: MaterialLeanBack) {
        materialLeanBackAdapter = MaterialLeanBackAdapter()

        GlobalScope.launch(Dispatchers.Main) {
            materialLeanBackAdapter.updateList(mockList1())
        }
        setupTitleCategoryTextView()
        materialLeanBackId.adapter = materialLeanBackAdapter
    }

    private fun setupTitleCategoryTextView() {
        material_lean_back_id.setCustomizer {
            it.textSize = 21f
            it.setTypeface(null, Typeface.BOLD)
        }
    }


}