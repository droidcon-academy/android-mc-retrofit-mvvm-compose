package com.droidcon.movieapp.data.repository

import com.droidcon.movieapp.common.Resource
import com.droidcon.movieapp.data.remote.models.MovieDetailsResponse
import com.droidcon.movieapp.data.remote.models.MoviesResponse
import com.droidcon.movieapp.data.remote.models.SearchedMovieResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    /** This function gets a list of top 250 movies from the IMDB api */
    fun getTop250Movies(): Flow<Resource<MoviesResponse>>

    /** This function searches movie by expression from the IMDB api */
    fun searchMovie(name: String): Flow<Resource<SearchedMovieResponse>>

    /** This function gets more details about a movie from the IMDB api */
    fun getMovieDetails(id: String): Flow<Resource<MovieDetailsResponse>>
}
