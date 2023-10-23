package com.droidcon.movieapp.data.remote

import retrofit2.Response

interface TMDBApi {

    fun getPopularMovies(): Response<MoviesResponse>

    fun searchMovie(): Response<MoviesResponse>

    fun getMovieDetails(): Response<MoviesResponse.MovieDetails>

    fun getSimilarMovies(): Response<MoviesResponse>
}
