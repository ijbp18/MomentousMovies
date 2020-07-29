package com.home.momentousmovies.ui.movieList

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.home.momentousmovies.R
import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.model.Movie
import com.home.momentousmovies.ui.movieList.adapter.MoviesAdapter
import com.home.momentousmovies.ui.movieList.viewModel.MoviesViewModel
import com.home.momentousmovies.utils.Constants
import com.home.momentousmovies.utils.Constants.DATE_SORT_DESC
import com.home.momentousmovies.utils.Constants.NAME_VALUE_DETAIL
import com.home.momentousmovies.utils.Constants.POPULARITY_DESC
import com.home.momentousmovies.utils.Constants.TITLE_SORT_ASC
import com.home.momentousmovies.utils.snackbar
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class HomeFragment : Fragment(), MoviesAdapter.ItemSelectedListener,
    SearchView.OnQueryTextListener, View.OnClickListener {

    private val viewModel: MoviesViewModel by sharedViewModel()
    private val moviesAdapter: MoviesAdapter = MoviesAdapter(this@HomeFragment)
    private lateinit var bottomSheetDialog : BottomSheetDialog

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
        setHasOptionsMenu(true)
    }

    private fun setupViewModel() {

        viewModel.movies.observe(viewLifecycleOwner, Observer { operation ->
            when (operation) {
                is OperationResult.Loading -> {
                    progressBarHome.visibility = View.VISIBLE
                    txt_empty.visibility = View.GONE
                }
                is OperationResult.Success -> {
                    operation.data?.let { movies ->
                        if (movies.isNotEmpty()) {
                            progressBarHome.visibility = View.GONE
                            txt_empty.visibility = View.GONE
                            showMovies(movies)
                        } else {
                            progressBarHome.visibility = View.GONE
                            showNoResults()
                        }
                    }
                }

                is OperationResult.Error -> {
                    operation.exception.message?.let { context?.snackbar(contentHome, it) }
                }
                is OperationResult.CustomError -> {
                    val typeError = if(operation.errorType == Constants.FAILURE_CONNECTION) getString(R.string.failure_network_connection) else getString(R.string.failure_unavailable)
                    context?.snackbar(contentHome, typeError )
                }
            }
        })
    }

    private fun initUI() {
        recycler_movie.adapter = moviesAdapter
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item, menu)
        val search = menu.findItem(R.id.action_search)
        val searchView = search.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort -> {
                showBottomSheetDialog()
                true
            }
            else -> {super.onOptionsItemSelected(item)
                false
            }
        }
    }

    private fun showBottomSheetDialog() {

        bottomSheetDialog = BottomSheetDialog(requireActivity())
        val view=layoutInflater.inflate(R.layout.bottom_sheet_layout,null)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()

        bottomSheetDialog.txtByName.setOnClickListener (this)
        bottomSheetDialog.txtByPopular.setOnClickListener (this)
        bottomSheetDialog.txtByRecent.setOnClickListener (this)

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


    override fun onMovieSelected(movie: Movie, sharedImageView: ImageView) {
        val bundle = bundleOf(NAME_VALUE_DETAIL to movie.id)
        view?.findNavController()?.navigate(R.id.movieDetailFragment, bundle)
    }

    override fun onQueryTextSubmit(query: String?)= false

    override fun onQueryTextChange(newText: String): Boolean {
        moviesAdapter.filterByQuery(newText.toLowerCase())
        return true
    }

    override fun onClick(v: View) {
        var sortType = ""
        when (v.id) {
            R.id.txtByPopular -> sortType = POPULARITY_DESC
            R.id.txtByName -> sortType = TITLE_SORT_ASC
            R.id.txtByRecent -> sortType = DATE_SORT_DESC
        }

        if(bottomSheetDialog.isShowing) bottomSheetDialog.dismiss()
        viewModel.retrieveMoviesBySort(sortType)
    }


}
