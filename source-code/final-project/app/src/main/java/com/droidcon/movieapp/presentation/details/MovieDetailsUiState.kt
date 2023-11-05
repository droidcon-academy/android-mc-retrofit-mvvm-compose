package com.droidcon.movieapp.presentation.details

import com.droidcon.movieapp.data.remote.MoviesResponse

/**
 * This function contains all the possible user interface states
 * a user can come across while interacting with the application
 */
data class MovieDetailsUiState(
    val isLoading: Boolean = false,
    val movieDetails: MovieDetailsUi? = null,
    val similarMovies: List<MoviesResponse.MovieDetails> = emptyList(),
    val errorMessage: String = "",
)

data class MovieDetailsUi(
    val id: Int?,
    val title: String,
    val averageVote: Double?,
    val moviePosterUrl: String,
    val totalVotes: Int?,
    val year: String,
    val overview: String,
    val genres: String,
)
