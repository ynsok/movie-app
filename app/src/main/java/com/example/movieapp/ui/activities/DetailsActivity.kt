package com.example.movieapp.ui.activities

import android.databinding.DataBindingUtil
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.movieapp.BR
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityDetailsBinding
import com.example.movieapp.models.Result
import com.example.movieapp.view.model.detail.DetailViewModel
import kotlinx.android.synthetic.main.activity_details.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

// TODO trailer video doesnt work for some movies ID (e.g. 530,540), works for (500,550)
// TODO revenue is often show as 0$ cos its saved as 0 in the database -> if revenue = 0, we should display that it is not known or hide revenue from view

class DetailsActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val detailViewModel: DetailViewModel by instance()
    lateinit var resultMovieObject: Result
    var isInFavorite: ((Boolean) -> Unit)? = null
    companion object {
        private const val MOVIE_ID = "movieId"
        fun getIntent(context: Context, movieId: Int): Intent =
            Intent(context, DetailsActivity::class.java).putExtra(MOVIE_ID, movieId)
    }

    // binding
    private lateinit var binding: ActivityDetailsBinding

    private val movieId: Int by lazy { intent.getIntExtra(MOVIE_ID, 0) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        // pass id on click from another fragment
        startFetchingById(movieId)
        isMovieInFavorite(movieId)
        getSuccessRespond()
        setUpDetailsToolbar()
        setUpDetailsCollapsingToolbar()
        initializeFavouritesFabAction()
    }

    private fun setUpDetailsToolbar() {
        setSupportActionBar(movie_details_toolbar_id)
        with(supportActionBar!!) {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setUpDetailsCollapsingToolbar() {
        with(movie_details_collapsing_toolbar_id) {
            setCollapsedTitleTextColor(Color.WHITE)
            setExpandedTitleColor(Color.WHITE)
        }
    }

    private fun isMovieInFavorite(movieId: Int) {
        val liveData = detailViewModel.getAllFavoriteMovies()
        liveData.observe(this, Observer { listOfFavoriteMovies ->
            listOfFavoriteMovies?.forEach {
                if (it.id == movieId) {
                    isInFavorite?.invoke(true)
                    return@Observer
                }
            }
            isInFavorite?.invoke(false)
            return@Observer
        })
    }

    private fun initializeFavouritesFabAction() {
        isInFavorite = { result ->
            if (result) {
                favourites_fab_id.setImageDrawable(getDrawable((R.drawable.ic_star_full_yellow)))
            } else {
                favourites_fab_id.setImageDrawable(getDrawable((R.drawable.ic_star_border_yellow)))
            }

            favourites_fab_id.setOnClickListener {
                if (result) {
                    detailViewModel.removeFromDatabase(resultMovieObject)
                    favourites_fab_id.setImageDrawable(getDrawable((R.drawable.ic_star_border_yellow)))
                    Toast.makeText(
                        this,
                        "${resultMovieObject.title} ${getString(R.string.deleted_movie_from_favorite_message)}",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    detailViewModel.addToDatabase(resultMovieObject)
                    favourites_fab_id.setImageDrawable(getDrawable((R.drawable.ic_star_full_yellow)))
                    Toast.makeText(
                        this,
                        " ${resultMovieObject.title} ${getString(R.string.added_movie_to_favorite_message)}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    /*  private fun initializeExoPlayerWithAddress(youtubeKey: String) {
          val youtubeLink = "http://youtube.com/watch?v=$youtubeKey"

          object : YouTubeExtractor(this) {
              public override fun onExtractionComplete(
                  ytFiles: SparseArray<YtFile>?,
                  vMeta: VideoMeta
              ) {
                  if (ytFiles != null) {
                      val itag = 22
                      val downloadUrl = ytFiles.get(itag).url
                  }
              }
          }.extract(youtubeLink, true, true)
      }*/

    private fun startFetchingById(id: Int) {
        detailViewModel.fetchMovieDetail(id)
        detailViewModel.fetchMovieDetailVideo(id)
        //Log.i("movie", resultMovieObject.toString())
    }

    private fun getSuccessRespond() {
        detailViewModel.getMovieDetailSuccess.observe(
            this,
            Observer {
                with(binding) {
                    setVariable(BR.movie, it!!)
                    executePendingBindings()
                }
                resultMovieObject =
                    Result(it!!.id, it.title, it.poster_path, it.vote_average, it.release_date)
            })

        detailViewModel.getMovieDetailVideoSuccess.observe(this, Observer { videoList ->
            //  if (videoList?.results?.size!! >= 0) initializeExoPlayerWithAddress(videoList.results.first().key)

        })
    }
}
