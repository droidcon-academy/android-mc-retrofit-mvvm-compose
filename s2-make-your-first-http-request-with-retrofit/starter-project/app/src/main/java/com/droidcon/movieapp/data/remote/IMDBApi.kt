package com.droidcon.movieapp.data.remote

interface IMDBApi {

    suspend fun getTop250Movies()

    suspend fun searchMovie()

    suspend fun getMovieDetails()
}
