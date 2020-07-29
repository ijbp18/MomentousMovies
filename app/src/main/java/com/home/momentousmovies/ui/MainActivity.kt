package com.home.momentousmovies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import com.home.momentousmovies.R
import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.ui.movieList.viewModel.MoviesViewModel
import com.home.momentousmovies.utils.Constants
import com.home.momentousmovies.utils.snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        configNav()
        setSupportActionBar(toolbar_main)
    }

    private fun initViewModel() {
        val viewModel : MoviesViewModel by viewModel()
        viewModel.token.observe(this, Observer {operation ->
            when (operation) {

                is OperationResult.Error -> {
                    operation.exception.message?.let {
                        snackbar(window.decorView.rootView, it)
                    }
                }
                is OperationResult.CustomError -> {
                    val typeError = if(operation.errorType == Constants.FAILURE_CONNECTION) getString(R.string.failure_network_connection) else getString(R.string.failure_unavailable)
                    snackbar(window.decorView.rootView, typeError)
                }
            }
        })
    }

    private fun configNav() = findNavController(this, R.id.nav_host_fragment)

}
