package com.droidcon.movieapp.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

    /** This endpoint gets popular movies */
    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = "aa90004b9c2baa5fa2306c25aa6d3af4",
    ): Response<MoviesResponse>

    /** This endpoint searches movie by expression */
    @GET("/3/search/movie")
    suspend fun searchMovie(
        @Query("query") movieName: String,
        @Query("api_key") apiKey: String = "aa90004b9c2baa5fa2306c25aa6d3af4",
    ): Response<MoviesResponse>

    /** This endpoint gets more details about a particular movie */
    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = "aa90004b9c2baa5fa2306c25aa6d3af4",
    ): Response<MoviesResponse.MovieDetails>

    @GET("/3/movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = "aa90004b9c2baa5fa2306c25aa6d3af4",
    ): Response<MoviesResponse>
}
