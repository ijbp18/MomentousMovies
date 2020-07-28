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
import com.home.momentousmovies.model.MovieInfo
import com.home.momentousmovies.ui.movieDetail.adapter.CastingAdapter
import com.home.momentousmovies.ui.movieList.viewModel.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movie_info.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MovieInfoFragment : Fragment() {

    private val viewModel: MoviesViewModel by sharedViewModel()
    private val castingAdapter: CastingAdapter =
        CastingAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        initRecyclerCasting()
    }

    private fun initRecyclerCasting() {
        rvCasting.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvCasting.adapter = castingAdapter
    }

    private fun setupViewModel() {
        viewModel.selectedMovie.observe(viewLifecycleOwner, Observer { operation ->
            when (operation) {
                is OperationResult.Success -> {
                    operation.data?.let { movieInfo ->
                        showMovieInfo(movieInfo)

                    }
                }
            }

        })
    }

    private fun showMovieInfo(movieInfo: MovieInfo) {

        movieInfo.apply {
            tvDescriptionInfoMovie.text = description
            tvReleaseInfoMovie.text = release_date
            tvStatusInfoMovie.text = status
            castingAdapter.setData(movieInfo.cast)
        }
    }


}
