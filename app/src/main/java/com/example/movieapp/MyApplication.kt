package com.example.movieapp

import android.app.Application
import com.example.movieapp.BuildConfig.KEY_API
import com.example.movieapp.network.MovieApiService
import com.example.movieapp.repositories.Repository
import com.example.movieapp.view.model.Browse.BrowseViewModelFactory
import com.example.movieapp.view.model.Detail.DetailViewModel
import com.example.movieapp.view.model.Genres.GenresViewModel
import com.example.movieapp.view.model.Home.HomeViewModelFactory
import com.example.movieapp.view.model.search.SearchViewModelFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : Application(), KodeinAware {
    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    override val kodein by Kodein.lazy {

        bind() from singleton {
            Interceptor { chain ->
                val newUrl =
                    chain
                        .request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("api_key", KEY_API)
                        .build()
                val newRequest =
                    chain
                        .request()
                        .newBuilder()
                        .url(newUrl)
                        .build()
                chain.proceed(newRequest)
            }
        }
        bind() from singleton {
            OkHttpClient()
                .newBuilder()
                .addInterceptor(instance())
                .build()
        }
        bind() from singleton { GsonConverterFactory.create() }
        bind() from singleton { CoroutineCallAdapterFactory() }
        bind() from singleton {
            Retrofit.Builder()
                .client(instance())
                .baseUrl(BASE_URL)
                .addConverterFactory(instance())
                .addCallAdapterFactory(instance())
                .build()
        }
        bind() from singleton { instance<Retrofit>().create(MovieApiService::class.java) }
        bind() from singleton { Repository(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
        bind() from provider { BrowseViewModelFactory(instance()) }
        bind() from provider { GenresViewModel(instance()) }
        bind() from provider { DetailViewModel(instance()) }
        bind() from provider { SearchViewModelFactory(instance()) }
    }
}