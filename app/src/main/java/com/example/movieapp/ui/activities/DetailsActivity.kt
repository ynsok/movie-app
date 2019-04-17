package com.example.movieapp.ui.activities

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.movieapp.R
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setUpDetailsToolbar()
        setUpDetailsCollapsingToolbar()

        favouritesFabAction()
    }

    private fun setUpDetailsToolbar() {
        setSupportActionBar(movie_details_toolbar_id)
        with(supportActionBar!!) {
            setDisplayHomeAsUpEnabled(true)
            title = "Movie Title"                       //movie title on collapsing bar is set here
        }
    }

    private fun setUpDetailsCollapsingToolbar() {
        with(movie_details_collapsing_toolbar_id) {
            setCollapsedTitleTextColor(Color.WHITE)
            setExpandedTitleColor(Color.WHITE)
        }
    }

    private fun favouritesFabAction() {
        var buttonState = 0

        favourites_fab_id.setOnClickListener {
            if (buttonState==0) {
                //if movie is not in favourites
                favourites_fab_id.setImageDrawable(getDrawable((R.drawable.ic_star_full_yellow)))
                buttonState = 1

            } else {
                //if movie is in favourites already
                favourites_fab_id.setImageDrawable(getDrawable((R.drawable.ic_star_border_yellow)))
                buttonState = 0
            }
        }
    }
}
