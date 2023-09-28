package com.droidcon.movieapp.data.repository

import com.droidcon.movieapp.common.Resource
import com.droidcon.movieapp.data.remote.models.MovieDetailsResponse
import com.droidcon.movieapp.data.remote.models.MoviesResponse
import com.droidcon.movieapp.data.remote.models.SearchedMovieResponse
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl : MoviesRepository {
    override fun getTop250Movies(): Flow<Resource<MoviesResponse>> {
        TODO("Not yet implemented")
    }

    override fun searchMovie(name: String): Flow<Resource<SearchedMovieResponse>> {
        TODO("Not yet implemented")
    }

    override fun getMovieDetails(id: String): Flow<Resource<MovieDetailsResponse>> {
        TODO("Not yet implemented")
    }
}