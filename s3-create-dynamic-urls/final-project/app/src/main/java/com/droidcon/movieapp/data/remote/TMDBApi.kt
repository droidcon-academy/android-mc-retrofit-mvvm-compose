package com.droidcon.movieapp.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApi {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = "aa90004b9c2baa5fa2306c25aa6d3af4",
    ): Response<MoviesResponse>

    fun searchMovie(): Response<MoviesResponse>

    fun getMovieDetails(): Response<MoviesResponse.MovieDetails>

    fun getSimilarMovies(): Response<MoviesResponse>
}
