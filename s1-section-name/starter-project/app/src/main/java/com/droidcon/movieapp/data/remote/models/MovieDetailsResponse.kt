package com.droidcon.movieapp.data.remote.models

data class MovieDetailsResponse(
    val id: String,
    val fullTitle: String,
    val image: String,
    val year: String,
    val plot: String,
    val actorList: List<Actor>,
    val genres: String,
    val imDbRating: String,
    val imDbRatingVotes: String,
    val trailer: Trailer,
    val similars: List<MoviesResponse.Item>,
    val errorMessage: String,
) {
    data class Actor(
        val id: String,
        val image: String,
        val name: String,
    )

    data class Trailer(
        val link: String,
    )
}
