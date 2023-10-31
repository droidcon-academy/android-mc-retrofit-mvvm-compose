package com.droidcon.movieapp.data.repository

import com.droidcon.movieapp.common.Resource
import com.droidcon.movieapp.data.remote.MoviesResponse
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl : MoviesRepository {
    override fun getPopularMovies(): Flow<Resource<MoviesResponse>> {
        TODO("Not yet implemented")
    }

    override fun searchMovie(name: String): Flow<Resource<MoviesResponse>> {
        TODO("Not yet implemented")
    }

    override fun getMovieDetails(movieId: Int): Flow<Resource<MoviesResponse.MovieDetails>> {
        TODO("Not yet implemented")
    }

    override fun getSimilarMovies(movieId: Int): Flow<Resource<MoviesResponse>> {
        TODO("Not yet implemented")
    }
}
