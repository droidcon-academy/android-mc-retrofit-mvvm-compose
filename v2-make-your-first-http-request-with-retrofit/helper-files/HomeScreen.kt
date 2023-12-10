package com.droidcon.movieapp.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.droidcon.movieapp.common.UNKNOWN
import com.droidcon.movieapp.common.UiEvents
import com.droidcon.movieapp.presentation.components.ErrorComponent
import com.droidcon.movieapp.presentation.components.LoadingComponent
import com.droidcon.movieapp.presentation.ui.theme.MovieAppTheme
import com.droidcon.retrofitposts.R
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    moviesViewModel: MoviesViewModel = hiltViewModel(),
    navigateToDetailsScreen: (String) -> Unit,
) {
    /** Used to show a SnackBar*/
    val scaffoldState = rememberScaffoldState()

    /** Observing UiEvents and if it SnackBarEvent it will show a SnackBar with a message if state is an error*/
    LaunchedEffect(key1 = true) {
        moviesViewModel.getPopularMovies()
        moviesViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackBarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    /**
     * Observing MoviesUiState and updating UI in accordance to if the state isLoading,
     * has data or no data,error and TextField states for searching for a particular movie
     */
    val moviesUiState = moviesViewModel.moviesUiState.collectAsState().value

    MovieAppTheme {
        Scaffold(scaffoldState = scaffoldState) {
            val spanCount = 2

            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color(0XFF080C2C)),
            ) {
                if (moviesUiState.isLoading) {
                    Box(modifier = Modifier.align(Alignment.Center)) {
                        LoadingComponent()
                    }
                }

                if (!moviesUiState.isLoading && moviesUiState.errorMessage.isNotEmpty()) {
                    Box(modifier = Modifier.align(Alignment.Center)) {
                        ErrorComponent(errorMessage = moviesUiState.errorMessage)
                    }
                }

                if (!moviesUiState.isLoading && moviesUiState.movies.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(spanCount),
                        contentPadding = PaddingValues(16.dp),
                    ) {
                        item(
                            span = {
                                GridItemSpan(spanCount)
                            },
                        ) {
                            SearchMovieTextField(
                                setTypedMovie = { typedMovie ->
                                    moviesViewModel.setTypedMovie(
                                        typedMovie = typedMovie,
                                    )
                                },
                                typedMovie = moviesUiState.typedMovie,
                                onClearClicked = {
                                    moviesViewModel.setTypedMovie("")
                                    moviesViewModel.getPopularMovies()
                                },
                                searchMovie = {
                                    moviesViewModel.searchMovie()
                                },
                            )
                        }

                        item(
                            span = {
                                GridItemSpan(spanCount)
                            },
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 24.dp,
                                        bottom = 16.dp,
                                    ),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text("Popular Movies", color = Color.White, fontSize = 16.sp)

                                Text("See more", color = Color.LightGray, fontSize = 12.sp)
                            }
                        }

                        items(moviesUiState.movies) { movie ->
                            MovieComponent(
                                posterImage = movie.moviePosterUrl.orEmpty(),
                                title = movie.title ?: UNKNOWN,
                                rating = movie.averageVote,
                                navigateToDetailScreen = {
                                    navigateToDetailsScreen(movie.id.toString())
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchMovieTextField(
    setTypedMovie: (String) -> Unit,
    typedMovie: String,
    onClearClicked: () -> Unit,
    searchMovie: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = typedMovie,
        onValueChange = {
            setTypedMovie(it)
        },
        placeholder = { Text("Search your movie", color = Color.LightGray) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                tint = Color.LightGray,
            )
        },
        trailingIcon = {
            if (typedMovie != "") {
                IconButton(onClick = { onClearClicked() }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Search",
                        tint = Color.LightGray,
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(50.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.LightGray,
            textColor = Color.LightGray,
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                searchMovie()
                keyboardController?.hide()
                focusManager.clearFocus()
            },
        ),
    )
}

@Composable
fun MovieComponent(
    posterImage: String,
    title: String,
    rating: Double?,
    navigateToDetailScreen: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navigateToDetailScreen()
            },
    ) {
        AsyncImage(
            model = posterImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.movie_placeholder),
            modifier = Modifier
                .height(300.dp)
                .clip(
                    RoundedCornerShape(14.dp),
                ),
        )

        Text(
            modifier = Modifier,
            text = title,
            color = Color.White,
        )

        if (rating != null) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(top = 4.dp),
            ) {
                Text(text = "$rating", color = Color.White)

                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color(0XFFff9e22),
                )
            }
        }
    }
}
