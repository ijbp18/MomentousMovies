package com.home.momentousmovies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import com.home.momentousmovies.R
import com.home.momentousmovies.ui.movieList.viewModel.MoviesViewModel
import kotlinx.android.synthetic.main.activity_main.*
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
        viewModel.token.observe(this, Observer {
            Toast.makeText(this, getString(R.string.failed_get_token), Toast.LENGTH_SHORT).show()
            finish()
        })
    }

    private fun configNav() = findNavController(this, R.id.nav_host_fragment)

}
