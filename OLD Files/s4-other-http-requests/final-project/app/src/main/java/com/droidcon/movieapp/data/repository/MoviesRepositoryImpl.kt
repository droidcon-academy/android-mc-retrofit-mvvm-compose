package com.droidcon.movieapp.data.repository

import com.droidcon.movieapp.common.Resource
import com.droidcon.movieapp.common.UNKNOWN_ERROR_OCCURRED
import com.droidcon.movieapp.data.remote.MoviesResponse
import com.droidcon.movieapp.data.remote.TMDBApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val TMDBApi: TMDBApi) : MoviesRepository {
    override fun getPopularMovies(): Flow<Resource<MoviesResponse>> = flow {
        val response = TMDBApi.getPopularMovies()

        if (response.isSuccessful) {
            emit(Resource.Success(data = response.body()))
        } else {
            val errorMessage = response.errorBody()?.string() ?: UNKNOWN_ERROR_OCCURRED
            emit(Resource.Error(message = errorMessage))
        }
    }.flowOn(Dispatchers.IO)

    override fun searchMovie(name: String): Flow<Resource<MoviesResponse>> = flow {
        val response = TMDBApi.searchMovie(movieName = name)

        if (response.isSuccessful) {
            emit(Resource.Success(data = response.body()))
        } else {
            val errorMessage = response.errorBody()?.string() ?: UNKNOWN_ERROR_OCCURRED

            emit(Resource.Error(message = errorMessage))
        }
    }.flowOn(Dispatchers.IO)

    override fun getMovieDetails(movieId: Int): Flow<Resource<MoviesResponse.MovieDetails>> = flow {
        val response = TMDBApi.getMovieDetails(movieId = movieId)

        if (response.isSuccessful) {
            emit(Resource.Success(data = response.body()))
        } else {
            val errorMessage = response.errorBody()?.string() ?: UNKNOWN_ERROR_OCCURRED

            emit(Resource.Error(message = errorMessage))
        }
    }.flowOn(Dispatchers.IO)
}
