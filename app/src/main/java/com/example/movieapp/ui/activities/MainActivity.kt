package com.example.movieapp.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.movieapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpToolbarMain()
        setUpBottomNavigationView()
    }

    private fun setUpToolbarMain() {
        setSupportActionBar(toolbar_main_activity_id)
        title = getString(R.string.main_activity_name)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
    }

    private fun setUpBottomNavigationView() {
        val navController = findNavController(R.id.my_nav_host_fragment)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }
}

