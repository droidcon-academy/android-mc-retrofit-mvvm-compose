package com.droidcon.movieapp.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.movieapp.common.NOT_AVAILABLE
import com.droidcon.movieapp.common.Resource
import com.droidcon.movieapp.common.SOMETHING_WENT_WRONG
import com.droidcon.movieapp.common.SOMETHING_WENT_WRONG_WHEN_FETCHING_SIMILAR_MOVIES
import com.droidcon.movieapp.common.UNKNOWN
import com.droidcon.movieapp.common.UiEvents
import com.droidcon.movieapp.common.roundOffDecimal
import com.droidcon.movieapp.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This is a state holder class for the UI, that emits & manages UI states, observed by the UI
 */
@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    /**
     * This is used to update and emit the MovieDetailsUiState i.e isLoading, posts & errorMessage
     * _moviesUiState is the mutable state that will be updated while
     * moviesUiState is the exposed state that is emitted to the UI
     * */
    private val _movieDetailsUiState = MutableStateFlow(MovieDetailsUiState())
    val movieDetailsUiState: StateFlow<MovieDetailsUiState> = _movieDetailsUiState.asStateFlow()

    /**
     * This is an emitter that sends user events to the UI, in this case showing a SnackBar.
     * _eventFlow is the mutable state that will be updated while
     * eventFlow is the exposed state that is emitted to the UI
     * */
    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow: SharedFlow<UiEvents> = _eventFlow.asSharedFlow()

    /**
     * This functions makes a call to the MoviesRepository to get more details about a particular movie,
     * then based on the response gotten it will update the UI states accordingly.
     * Encase of an error it will send a SnackBar event to show an error message on the screen
     */
    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _movieDetailsUiState.value =
                movieDetailsUiState.value.copy(
                    isLoading = true,
                )

            moviesRepository.getMovieDetails(movieId = movieId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val movieDetails = MovieDetailsUi(
                            id = result.data?.id,
                            title = result.data?.title ?: UNKNOWN,
                            averageVote = result.data?.averageVote?.roundOffDecimal(),
                            moviePosterUrl = "https://image.tmdb.org/t/p/w220_and_h330_face/${result.data?.moviePosterUrl}",
                            totalVotes = result.data?.totalVotes,
                            year = result.data?.year ?: NOT_AVAILABLE,
                            overview = result.data?.overview ?: UNKNOWN,
                            genres = result.data?.genres?.map { it.name }?.joinToString(", ")
                                ?: UNKNOWN,
                        )

                        _movieDetailsUiState.value = movieDetailsUiState.value.copy(
                            isLoading = false,
                            movieDetails = movieDetails,
                        )

                        getSimilarMovies(movieId = movieId)
                    }

                    is Resource.Error -> {
                        _movieDetailsUiState.value = movieDetailsUiState.value.copy(
                            isLoading = false,
                            errorMessage = result.message ?: SOMETHING_WENT_WRONG,
                        )

                        _eventFlow.emit(
                            UiEvents.SnackBarEvent(
                                message = result.message ?: SOMETHING_WENT_WRONG,
                            ),
                        )
                    }

                    else -> {
                        movieDetailsUiState
                    }
                }
            }
        }
    }
}
