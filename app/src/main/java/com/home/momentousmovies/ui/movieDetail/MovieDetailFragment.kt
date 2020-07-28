package com.home.momentousmovies.ui.movieDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

import com.home.momentousmovies.R
import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.data.network.Endpoints
import com.home.momentousmovies.model.Movie
import com.home.momentousmovies.model.MovieInfo
import com.home.momentousmovies.ui.movieList.viewModel.MoviesViewModel
import com.home.momentousmovies.utils.Constants
import com.home.momentousmovies.utils.buildImageUrl
import com.home.momentousmovies.utils.loadImage
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailFragment : Fragment() {

    private val viewModel: MoviesViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val placeSelected = getBundleExtra()
        configViewPager()
        setupViewModel()
        setHasOptionsMenu(true)
        if (placeSelected != null) {
            viewModel.retrieveSelectedMovie(placeSelected)
        }

    }

    private fun configViewPager() {
        view_pager_detail.adapter = HomeDetailPagerAdapter(this)
        TabLayoutMediator(tabs, view_pager_detail) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MY_INFO_MOVIE_PAGE_INDEX -> getString(R.string.txt_overview)
            REVIEW_PAGE_INDEX -> getString(R.string.reviews)
            else -> null
        }
    }

    private fun setupViewModel() {
        viewModel.selectedMovie.observe(viewLifecycleOwner, Observer { operation ->

            when (operation) {
                is OperationResult.Loading -> {
                    progressBar_.visibility = View.VISIBLE
                }
                is OperationResult.Success -> {
                    operation.data?.let { movie ->
                        progressBar_.visibility = View.GONE
                        showMovieInfo(movie)
                    }
                }

                is OperationResult.Error -> {
                    showSnackbar(operation.throwable.message)
                }
            }

        })
    }

    private fun showMovieInfo(movie: MovieInfo) {

        toolbar_layout.title = movie.title.toUpperCase()
        tv_movie_title.text = movie.title.toUpperCase()
        detail_image.loadImage(movie.image.buildImageUrl(Endpoints.URL_BASE, Endpoints.GET_IMAGE))


    }

    private fun getBundleExtra() = arguments?.getInt(Constants.NAME_VALUE_DETAIL)

    private fun showSnackbar(message: String?) {
        Snackbar.make(
            root_content,
            message ?: getString(R.string.failed_get_data),
            Snackbar.LENGTH_LONG
        ).show()
    }
}
