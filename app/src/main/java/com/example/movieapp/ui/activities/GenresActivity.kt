package com.example.movieapp.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.movieapp.R
import kotlinx.android.synthetic.main.activity_genres.*

class GenresActivity : AppCompatActivity() {

    //TODO app should go back to 'browse' fragment 'onBackPress' instead of going to 'home' fragment
    //TODO going back to home is set in manifest

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
