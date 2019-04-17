package com.example.movieapp.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }

/*
    fun onBackPressed() {
        val currentFragment = nav_host_fragment_id.childFragmentManager.fragments[0]
        val controller = Navigation.findNavController(context, R.id.nav_host_fragment_id)
        if (currentFragment is OnBackPressedListener)
            (currentFragment as OnBackPressedListener).onBackPressed()
        else if (!controller.popBackStack())
            finish()

    }
    */
}
