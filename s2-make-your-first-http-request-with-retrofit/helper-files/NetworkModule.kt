package com.droidcon.movieapp.di

import com.droidcon.movieapp.data.remote.TMDBApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /** This function creates an OKHttp client that will be used to create a Retrofit instance */
    private fun loggingInterceptor(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    /**
     * This function creates and provides a Retrofit instance,
     * that will be used to communicate with JsonPlaceholder API
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(
                "https://api.themoviedb.org",
            )
            .addConverterFactory(GsonConverterFactory.create())
            .client(loggingInterceptor())
            .build()
    }

    /**
     * This function uses the provided Retrofit instance,
     * that will be used to make network calls to the JsonPlaceHolder API
     */
    @Provides
    @Singleton
    fun provideIMDBApi(retrofit: Retrofit): TMDBApi {
        return retrofit.create()
    }
}
