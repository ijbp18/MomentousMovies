package com.home.momentousmovies.ui.movieList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.home.momentousmovies.R
import com.home.momentousmovies.domain.MovieRepositoryImpl
import com.home.momentousmovies.model.Movie
import com.home.momentousmovies.ui.movieList.adapter.MoviesAdapter
import com.home.momentousmovies.ui.movieList.viewModel.MovieViewModelFactory
import com.home.momentousmovies.ui.movieList.viewModel.MoviesViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: MoviesViewModel

    private val moviesAdapter: MoviesAdapter =
        MoviesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initViewModel()
        initUI()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, MovieViewModelFactory(MovieRepositoryImpl())).get(
            MoviesViewModel::class.java)
        viewModel.movies.observe(this, renderMovies)
    }

    private fun initUI() {
        recycler_movie.adapter = moviesAdapter
    }

    private val renderMovies = Observer<List<Movie>> {
        moviesAdapter.setData(it)
    }
}
