package com.example.movieapp.ui.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.movieapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    companion object {
        fun getIntent(context: Context): Intent = Intent(context, DetailsActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setUpDetailsToolbar()
        setUpDetailsCollapsingToolbar()

        favouritesFabAction()

        Picasso.get().load("https://image.tmdb.org/t/p/w500/adw6Lq9FiC9zjYEpOqfq03ituwp.jpg")
            .into(movie_image_dl_img_details)


    }

    private fun setUpDetailsToolbar() {
        setSupportActionBar(movie_details_toolbar_id)
        with(supportActionBar!!) {
            setDisplayHomeAsUpEnabled(true)
            title = "Fight Club"                       //movie title on collapsing bar is set here
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
            if (buttonState == 0) {
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
