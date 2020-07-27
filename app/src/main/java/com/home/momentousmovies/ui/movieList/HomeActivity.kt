package com.home.momentousmovies.ui.movieList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.home.momentousmovies.R
import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.model.Movie
import com.home.momentousmovies.ui.movieList.adapter.MoviesAdapter
import com.home.momentousmovies.ui.movieList.viewModel.MoviesViewModel
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel: MoviesViewModel by viewModel()
    private val moviesAdapter: MoviesAdapter = MoviesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initViewModel()
        initUI()
    }

    private fun initViewModel() {

        viewModel.token.observe(this, Observer { tokenErrorMessage ->
            showSnackbar(tokenErrorMessage)
        })

        viewModel.movies.observe(this, Observer { operation ->

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
}
