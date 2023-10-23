package com.droidcon.movieapp.presentation.home

import com.droidcon.movieapp.data.remote.MoviesResponse

/**
 * This function contains all the possible user interface states
 * a user can come across while interacting with the application
 */
data class MoviesUiState(
    val isLoading: Boolean = false,
    val movies: List<MoviesResponse.MovieDetails>? = null,
    val errorMessage: String? = null,
    val typedMovie: String = "",
)
