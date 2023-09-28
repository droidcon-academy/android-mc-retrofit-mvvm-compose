package com.droidcon.movieapp.data.remote.models

data class MoviesResponse(
    val errorMessage: String,
    val items: List<Item>,
) {
    data class Item(
        val id: String,
        val imDbRating: String? = null,
        val image: String,
        val title: String,
    )
}
