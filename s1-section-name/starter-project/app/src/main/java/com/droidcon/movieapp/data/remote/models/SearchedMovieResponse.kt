package com.droidcon.movieapp.data.remote.models

data class SearchedMovieResponse(
    val searchType: String,
    val expression: String,
    val results: List<MoviesResponse.Item>,
    val errorMessage: String
)
