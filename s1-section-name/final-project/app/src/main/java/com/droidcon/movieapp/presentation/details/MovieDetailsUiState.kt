package com.droidcon.movieapp.presentation.details

import com.droidcon.movieapp.data.remote.models.MovieDetailsResponse

/**
 * This function contains all the possible user interface states
 * a user can come across while interacting with the application
 */
data class MovieDetailsUiState(
    val isLoading: Boolean = false,
    val movieDetails: MovieDetailsResponse? = null,
    val errorMessage: String? = null
)
