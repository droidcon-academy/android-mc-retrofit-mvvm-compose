package com.droidcon.movieapp.di

import com.droidcon.movieapp.data.remote.TMDBApi
import com.droidcon.movieapp.data.repository.MoviesRepository
import com.droidcon.movieapp.data.repository.MoviesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * This function provides an implementation of the MoviesRepository to be used in different
     * places of the application
     */
    @Provides
    @Singleton
    fun provideMoviesRepository(
        TMDBApi: TMDBApi,
    ): MoviesRepository {
        return MoviesRepositoryImpl(TMDBApi = TMDBApi)
    }
}
