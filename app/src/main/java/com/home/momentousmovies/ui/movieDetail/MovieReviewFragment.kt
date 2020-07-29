package com.home.momentousmovies.ui.movieDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.home.momentousmovies.R
import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.domain.model.Review
import com.home.momentousmovies.ui.movieDetail.adapter.ReviewAdapter
import com.home.momentousmovies.ui.movieList.viewModel.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movie_review.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class MovieReviewFragment : Fragment() {

    private val viewModel: MoviesViewModel by sharedViewModel()
    private val reviewAdapter: ReviewAdapter =
        ReviewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        initUI()
    }

    private fun initUI() {
        recycler_movie_review.layoutManager = LinearLayoutManager(activity)
        recycler_movie_review.adapter = reviewAdapter
    }


    private fun setupViewModel() {
        viewModel.selectedMovie.observe(viewLifecycleOwner, Observer { operation ->
            when (operation) {
                is OperationResult.Success -> {
                    operation.data?.let { movieInfo ->
                        if (movieInfo.reviews.isNullOrEmpty()) {
                            txt_empty_review.visibility = View.VISIBLE
                            txt_empty_review.text = getString(R.string.empty_reviews)
                            recycler_movie_review.visibility = View.GONE
                        } else {
                            txt_empty_review.visibility = View.GONE
                            showReviews(movieInfo.reviews)
                        }
                    }
                }
            }

        })
    }

    private fun showReviews(reviews: List<Review>) {
        reviewAdapter.setData(reviews)
    }

}
