package com.home.momentousmovies.di

import com.home.momentousmovies.data.datasource.DataSourceImpl
import com.home.momentousmovies.data.datasource.ApiService
import com.home.momentousmovies.data.datasource.Endpoints.URL_BASE
import com.home.momentousmovies.data.datasource.AuthInterceptor
import com.home.momentousmovies.data.datasource.repository.TokenRepository
import com.home.momentousmovies.data.datasource.repository.TokenRepositoryImpl
import com.home.momentousmovies.data.datasource.repository.MovieRepository
import com.home.momentousmovies.data.datasource.repository.MovieRepositoryImpl
import com.home.momentousmovies.data.preference.AppPreferenceHelper
import com.home.momentousmovies.data.preference.PreferenceHelper
import com.home.momentousmovies.ui.movieList.viewModel.MoviesViewModel
import com.home.momentousmovies.utils.NetworkHandler
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModule = module {
    viewModel { MoviesViewModel(repository = get(), repositoryToken = get()) }
}

val networkModule = module {
    single { createOkHttpClient(preferenceHelper = get()) }
    single { createWebService<ApiService>(okHttpClient = get(), baseUrl = URL_BASE) }
}

val dataSourceModule = module {
    single { DataSourceImpl(apiService = get()) }
}

val repositoryModule = module {
    single {
        MovieRepositoryImpl(
            networkHandler = get(),
            remoteDataSource = get()
        ) as MovieRepository
    }
    single {
        TokenRepositoryImpl(
            networkHandler = get(),
            apiService = get(),
            preferenceHelper = get()
        ) as TokenRepository
    }
}

val networkHandlerModule: Module = module {
    single { NetworkHandler(context = get()) }
}

val preferenceModule: Module = module {
    single { AppPreferenceHelper(context = get()) as PreferenceHelper }
}


private fun createOkHttpClient(preferenceHelper: PreferenceHelper): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor(preferenceHelper)).build()
}


inline fun <reified T> createWebService(okHttpClient: OkHttpClient, baseUrl: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}

val appModule = listOf(
    repositoryModule, viewModelModule,
    networkModule, dataSourceModule,
    networkHandlerModule, preferenceModule
)

