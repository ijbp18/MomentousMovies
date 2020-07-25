package com.home.momentousmovies.ui.movieList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.home.momentousmovies.BuildConfig
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
        viewModel.token.observe(this, renderToken )
        viewModel.movies.observe(this, renderMovies)
    }

    private fun initUI() {
        recycler_movie.adapter = moviesAdapter
    }

    private val renderToken = Observer<String> {token ->
        Toast.makeText(this, "TOKEN RECUPERADO: $token", Toast.LENGTH_SHORT).show()
    }

    private val renderMovies = Observer<List<Movie>> {
        moviesAdapter.setData(it)
    }

    private fun showError(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
