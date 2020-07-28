package com.home.momentousmovies.ui.movieList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar

import com.home.momentousmovies.R
import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.model.Movie
import com.home.momentousmovies.ui.movieList.adapter.MoviesAdapter
import com.home.momentousmovies.ui.movieList.viewModel.MoviesViewModel
import com.home.momentousmovies.utils.Constants.NAME_VALUE_DETAIL
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class HomeFragment : Fragment(), MoviesAdapter.ItemSelectedListener {

    private val viewModel: MoviesViewModel by sharedViewModel()
//    private val viewModel by viewModel<MoviesViewModel>()
    private val moviesAdapter: MoviesAdapter = MoviesAdapter(this@HomeFragment)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        initUI()

    }

    private fun setupViewModel() {

        viewModel.movies.observe(viewLifecycleOwner, Observer { operation ->
            when (operation) {
                is OperationResult.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    txt_empty.visibility = View.GONE
                }
                is OperationResult.Success -> {
                    operation.data?.let { movies ->
                        if (movies.isNotEmpty()) {
                            progressBar.visibility = View.GONE
                            txt_empty.visibility = View.GONE
                            showMovies(movies)
                        } else {
                            progressBar.visibility = View.VISIBLE
                            showNoResults()
                        }
                    }
                }

                is OperationResult.Error -> {
                    showSnackbar(operation.throwable.message)
                }
            }
        })
    }

    private fun showMovies(movies: List<Movie>) {
        moviesAdapter.removeAll()
        moviesAdapter.setData(movies)
    }

    private fun showNoResults() {
        moviesAdapter.setData(emptyList())
        txt_empty.visibility = View.VISIBLE
        txt_empty.text = getString(R.string.no_results_found)

    }

    private fun initUI() {
        recycler_movie.adapter = moviesAdapter

    }

    private fun showSnackbar(message: String?) {
        Snackbar.make(
            content,
            message ?: getString(R.string.failed_get_data),
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onMovieSelected(movie: Movie, sharedImageView: ImageView) {
        val bundle = bundleOf(NAME_VALUE_DETAIL to movie.id)
        view?.findNavController()?.navigate(R.id.movieDetailFragment, bundle)
    }

}
