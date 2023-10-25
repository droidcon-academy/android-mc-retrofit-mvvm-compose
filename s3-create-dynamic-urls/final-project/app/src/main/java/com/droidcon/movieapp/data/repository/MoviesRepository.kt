package com.droidcon.movieapp.data.repository

import com.droidcon.movieapp.common.Resource
import com.droidcon.movieapp.data.remote.MoviesResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    /** This function gets a list of popular 250 movies from the TMDB api */
    fun getPopularMovies(): Flow<Resource<MoviesResponse>>

    /** This function searches movie by name from the TMDB api */
    fun searchMovie(name: String): Flow<Resource<MoviesResponse>>

    /** This function gets more details about a movie from the TMDB api */
    fun getMovieDetails(movieId: Int): Flow<Resource<MoviesResponse.MovieDetails>>
}
