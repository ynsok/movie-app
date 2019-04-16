package com.example.movieapp.ui.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.movieapp.R
import kotlinx.android.synthetic.main.activity_genres.*

class GenresActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context): Intent  = Intent(context, GenresActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genres)

        setUpToolbarGenresActivity()
    }

    private fun setUpToolbarGenresActivity() {
        setSupportActionBar(toolbar_genres_activity_id)
        title = getString(R.string.genre_activity_title)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
    }
}
