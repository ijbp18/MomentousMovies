    package com.home.momentousmovies.di

    import android.app.Application
    import android.content.SharedPreferences
    import com.home.momentousmovies.data.network.ApiService
    import com.home.momentousmovies.data.network.Endpoints.URL_BASE
    import com.home.momentousmovies.data.network.AuthInterceptor
    import com.home.momentousmovies.domain.MovieRepository
    import com.home.momentousmovies.domain.MovieRepositoryImpl
    import com.home.momentousmovies.domain.TokenRepository
    import com.home.momentousmovies.domain.TokenRepositoryImpl
    import com.home.momentousmovies.ui.movieList.viewModel.MoviesViewModel
    import okhttp3.OkHttpClient
    import org.koin.android.ext.koin.androidApplication
    import org.koin.android.viewmodel.dsl.viewModel
    import org.koin.dsl.module
    import retrofit2.Retrofit
    import retrofit2.converter.gson.GsonConverterFactory
    import java.util.concurrent.TimeUnit

    val viewModelModule = module {
        viewModel { MoviesViewModel(get(), get()) }
    }

    val apiServiceModule = module {
        single { createOkHttpClient() }
        single {
            createWebService<ApiService>(get(), URL_BASE)
        }
    }

    val RepositoryModule = module {
        single<MovieRepository> { MovieRepositoryImpl(get()) }
        single<TokenRepository> { TokenRepositoryImpl(get()) }
    }

    val appModulePreference = module {

        single{
            getSharedPrefs(androidApplication())
        }

        single<SharedPreferences.Editor> {
            getSharedPrefs(androidApplication()).edit()
        }
    }

    private fun getSharedPrefs(androidApplication: Application): SharedPreferences{
        return  androidApplication.getSharedPreferences("default",  android.content.Context.MODE_PRIVATE)
    }

    private val apiKeyInterceptor by lazy { AuthInterceptor() }


    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(apiKeyInterceptor).build()
    }


    inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(T::class.java)
    }

    val appModule = listOf(
        RepositoryModule, viewModelModule,
        apiServiceModule, appModulePreference
    )

