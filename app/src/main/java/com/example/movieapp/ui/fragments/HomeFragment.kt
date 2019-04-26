package com.example.movieapp.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.models.AdapterPosition
import com.example.movieapp.network.result.Result
import com.example.movieapp.ui.activities.DetailsActivity
import com.example.movieapp.ui.adapters.HomeVerticalRecyclerView
import com.example.movieapp.view.model.home.HomeViewModel
import com.example.movieapp.view.model.home.HomeViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance

class HomeFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val homeViewModelFactory: HomeViewModelFactory by instance()
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeVerticalRecyclerView: HomeVerticalRecyclerView
    private var verticalAdapterPosition = 0
    private var adapterPosition: MutableList<Int> = mutableListOf(ZERO, ZERO, ZERO, ZERO)
    private fun headerList(): ArrayList<String> = arrayListOf(
        getString(R.string.category_popular),
        getString(R.string.category_upcoming),
        getString(R.string.category_now_playing),
        getString(R.string.category_top_rated)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instantiateHomeViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        getErrorRespond(view)
        getExceptionRespond(view)
        getSuccessRespond(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeSnackBar(view.rootView.home_container_id).dismiss()
        initializeViews()
        if (savedInstanceState != null) {
            verticalAdapterPosition = savedInstanceState.getInt(VERTICAL_POSITION)
            val adapterPosition =
                savedInstanceState.getParcelable(HORIZONTAL_POSITION) as AdapterPosition
            this.adapterPosition = adapterPosition.horizontalVerticalPosition
            master_home_rv_id.layoutManager?.scrollToPosition(verticalAdapterPosition)
            homeVerticalRecyclerView.horizontalListPosition = this.adapterPosition
        }
        getAdapterPositions()
        verticalAdapterPosition = getVerticalRecyclerPosition(view.master_home_rv_id)
        startDetailActivity()
    }

    private fun initializeViews() {
        homeVerticalRecyclerView = HomeVerticalRecyclerView(adapterPosition, headerList())
        master_home_rv_id.adapter = homeVerticalRecyclerView
    }

    private fun instantiateHomeViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, homeViewModelFactory).get(HomeViewModel::class.java)
    }

    private fun getSuccessRespond(view: View) {
        homeViewModel.getMovies().observe(this, Observer {
            when (it) {
                is Result.Success -> {
                    hideProgressBar(view)
                    homeVerticalRecyclerView.swapMovie(it.data)

                }
            }
        })
    }

    private fun hideProgressBar(view: View) {
        view.home_progressBar_id.visibility = View.GONE
        view.master_home_rv_id.visibility = View.VISIBLE
    }

    private fun visibleProgressBar(view: View) {
        view.home_progressBar_id.visibility = View.VISIBLE
        view.master_home_rv_id.visibility = View.GONE
    }

    private fun getErrorRespond(view: View) {
        homeViewModel.getMoviesError().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Result.Respond -> {
                    hideProgressBar(view)
                    setActionSnackBar(view.rootView.home_container_id, getString(R.string.again))
                }
            }
        })
    }

    private fun getExceptionRespond(view: View) {
        homeViewModel.getMoviesException().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Result.Respond -> {
                    hideProgressBar(view)
                    setActionSnackBar(
                        view.rootView.home_container_id,
                        getString(R.string.connection)
                    )
                }
            }
        })
    }

    private fun getVerticalRecyclerPosition(recyclerView: RecyclerView): Int {
        val layoutManage = recyclerView.layoutManager as LinearLayoutManager
        return layoutManage.findFirstVisibleItemPosition()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(VERTICAL_POSITION, verticalAdapterPosition)
        outState.putParcelable(HORIZONTAL_POSITION, AdapterPosition(adapterPosition))
        super.onSaveInstanceState(outState)
    }

    private fun getAdapterPositions() {
        homeVerticalRecyclerView.saveData = { verticalPosition: Int, horizontalPosition: Int ->

            when (verticalPosition) {
                ZERO -> adapterPosition.add(ZERO, horizontalPosition)
                ONE -> adapterPosition.add(ONE, horizontalPosition)
                TWO -> adapterPosition.add(TWO, horizontalPosition)
                THREE -> adapterPosition.add(THREE, horizontalPosition)
            }
        }
    }

    private fun setActionSnackBar(view: View, message: String) =
        initializeSnackBar(view, message).setAction(getString(R.string.Retry)) {
            visibleProgressBar(view)
            startFetchingRemoteData()
        }.show()

    private fun initializeSnackBar(view: View, message: String = "") =
        Snackbar.make(
            view.home_container_id,
            message,
            Snackbar.LENGTH_INDEFINITE
        )

    private fun startDetailActivity() {
        homeVerticalRecyclerView.sendId =
            { startActivity(DetailsActivity.getIntent(this.context!!, it)) }
    }

    private fun startFetchingRemoteData() = homeViewModel.startFetchingMovie()

    companion object {
        private const val VERTICAL_POSITION = "positionVertical"
        private const val HORIZONTAL_POSITION = "positionHorizontal"
        private const val ZERO = 0
        private const val ONE = 0
        private const val TWO = 0
        private const val THREE = 0
    }
}