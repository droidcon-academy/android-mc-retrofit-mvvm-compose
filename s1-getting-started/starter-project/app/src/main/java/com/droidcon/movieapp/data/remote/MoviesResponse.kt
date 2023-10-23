package com.droidcon.movieapp.data.remote

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    val page: Int,
    val results: List<MovieDetails>,
) {
    data class MovieDetails(
        val id: Int?,
        val title: String?,
        @SerializedName("vote_average")
        val averageVote: Double?,
        @SerializedName("poster_path")
        val moviePosterUrl: String?,
        @SerializedName("vote_count")
        val totalVotes: Int?,
        @SerializedName("release_date")
        val year: String?,
        val overview: String?,
        val genres: List<Genre>?,
    ) {
        data class Genre(
            val id: Int?,
            val name: String?,
        )
    }
}
