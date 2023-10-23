package com.droidcon.movieapp.presentation.home

import androidx.lifecycle.ViewModel
import com.droidcon.movieapp.common.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * This is a state holder class for the UI, that emits & manages UI states, observed by the UI
 */
@HiltViewModel
class MoviesViewModel @Inject constructor() : ViewModel() {

    /**
     * This is used to update and emit the MoviesUiState i.e isLoading, posts & errorMessage
     * _moviesUiState is the mutable state that will be updated while
     * moviesUiState is the exposed state that is emitted to the UI
     * */
    private val _moviesUiState = MutableStateFlow(MoviesUiState())
    val moviesUiState: StateFlow<MoviesUiState> = _moviesUiState.asStateFlow()

    /**
     * This is an emitter that sends user events to the UI, in this case showing a SnackBar.
     * _eventFlow is the mutable state that will be updated while
     * eventFlow is the exposed state that is emitted to the UI
     * */
    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow: SharedFlow<UiEvents> = _eventFlow.asSharedFlow()

    /**
     * This functions makes a call to the MoviesRepository to get a list of popular movies,
     * then based on the response gotten it will update the UI states accordingly.
     * Encase of an error it will send a SnackBar event to show an error message on the screen
     */
    fun getPopularMovies() {}

    /**
     * This functions makes a call to the MoviesRepository to search a movie by name,
     * then based on the response gotten it will update the UI states accordingly.
     * Encase of an error it will send a SnackBar event to show an error message on the screen
     */
    fun searchMovie() {}

    fun setTypedMovie(typedMovie: String) {}
}
