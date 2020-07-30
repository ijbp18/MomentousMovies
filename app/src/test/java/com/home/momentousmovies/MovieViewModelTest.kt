package com.home.momentousmovies

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.data.datasource.model.TokenResponse
import com.home.momentousmovies.domain.model.Movie
import com.home.momentousmovies.ui.movieList.viewModel.MoviesViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MovieViewModelTest {

    @Mock
    private lateinit var context: Application

    private lateinit var viewModel: MoviesViewModel

    private lateinit var token: Observer<List<TokenResponse>>
    private lateinit var renderMovieList: Observer<OperationResult<List<Movie>>>
    private lateinit var renderMovieSelected: Observer<OperationResult<Movie>>


    private val fakeMovieRepository = FakeMovieRepository()
    private val fakeErrorMovieRepository = FakeErrorMovieRepository()
    private val fakeTokenRepository = FakeTokenRepository()

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`<Context>(context.applicationContext).thenReturn(context)
        Dispatchers.setMain(testDispatcher)

        setupObservers()
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `sample test for LiveData`() {
        // Given
        val mutableLiveData = MutableLiveData<String>()

        // When
        mutableLiveData.postValue("test")

        // Then
        assertEquals("test", mutableLiveData.value)
    }


    @Test
    fun `retrieve movies with ViewModel and Repository returns full data`() {

        viewModel= MoviesViewModel(fakeMovieRepository, fakeTokenRepository)

        with(viewModel) {
            retrieveMovies()
            movies.observeForever(renderMovieList)
        }

        runBlockingTest {
            val response = fakeMovieRepository.getMovies()
            Assert.assertTrue(response is OperationResult.Success)
            Assert.assertTrue(viewModel.movies.value?.data?.size==3)
        }
    }

    @Test
    fun `retrieve movies with ViewModel and Repository returns an error`() {

        viewModel= MoviesViewModel(fakeErrorMovieRepository, fakeTokenRepository)
        with(viewModel) {
            retrieveMovies()
            movies.observeForever(renderMovieList)
        }

        runBlockingTest {
            val response = fakeErrorMovieRepository.getMovies()
            Assert.assertTrue(response is OperationResult.Error)
        }
    }

    @Test
    fun `retrieve movie item with ViewModel and Repository returns data item`() {

        val expectedItemId = 5
        viewModel= MoviesViewModel(fakeMovieRepository, fakeTokenRepository)

        with(viewModel) {
            retrieveSelectedMovie(expectedItemId)
            selectedMovie.observeForever(renderMovieSelected)
        }

        runBlockingTest {
            val response = fakeMovieRepository.getSelectedMovie(expectedItemId)
            Assert.assertTrue(response is OperationResult.Success)
            Assert.assertTrue(viewModel.selectedMovie.value?.data?.id==expectedItemId)
        }
    }

    private fun setupObservers() {
        renderMovieList = Mockito.mock(Observer::class.java) as Observer<OperationResult<List<Movie>>>
        renderMovieSelected = Mockito.mock(Observer::class.java) as Observer<OperationResult<Movie>>
    }


}