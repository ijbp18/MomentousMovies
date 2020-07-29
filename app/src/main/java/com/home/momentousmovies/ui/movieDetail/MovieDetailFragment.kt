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
import com.home.momentousmovies.data.datasource.Endpoints
import com.home.momentousmovies.domain.model.Movie
import com.home.momentousmovies.ui.movieDetail.adapter.HomeDetailPagerAdapter
import com.home.momentousmovies.ui.movieDetail.adapter.MY_INFO_MOVIE_PAGE_INDEX
import com.home.momentousmovies.ui.movieDetail.adapter.REVIEW_PAGE_INDEX
import com.home.momentousmovies.ui.movieList.viewModel.MoviesViewModel
import com.home.momentousmovies.utils.Constants
import com.home.momentousmovies.utils.buildImageUrl
import com.home.momentousmovies.utils.loadImage
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

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
        if (placeSelected != null) {
            viewModel.retrieveSelectedMovie(placeSelected)
        }

    }

    private fun configViewPager() {
        view_pager_detail.adapter =
            HomeDetailPagerAdapter(
                this
            )
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
                    progressBarDetail.visibility = View.VISIBLE
                }
                is OperationResult.Success -> {
                    operation.data?.let { movie ->
                        progressBarDetail.visibility = View.GONE
                        showMovieInfo(movie)
                    }
                }

                is OperationResult.Error -> {
                    progressBarDetail.visibility = View.GONE
                    showSnackbar(operation.exception.message)
                }
            }

        })
    }

    private fun showMovieInfo(movie: Movie) {

        tv_movie_title.text = movie.title.toUpperCase()
        if (!movie.image.isNullOrEmpty())
            detail_image.loadImage(movie.image.buildImageUrl(Endpoints.URL_BASE, Endpoints.GET_IMAGE))

    }

    private fun getBundleExtra() = arguments?.getInt(Constants.NAME_VALUE_DETAIL)

    private fun showSnackbar(message: String?) {
        Snackbar.make(root_content, message ?: getString(R.string.failed_get_data), Snackbar.LENGTH_SHORT).show()
    }
}
