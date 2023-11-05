package com.droidcon.movieapp.presentation.details

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.droidcon.movieapp.common.UiEvents
import com.droidcon.movieapp.presentation.components.ErrorComponent
import com.droidcon.movieapp.presentation.components.LoadingComponent
import com.droidcon.movieapp.presentation.ui.theme.MovieAppTheme
import com.droidcon.retrofitposts.R
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(
    movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel(),
    movieId: Int,
    navigateToHomeScreen: () -> Unit,
) {
    /** Used to show a SnackBar*/
    val scaffoldState = rememberScaffoldState()

    /** Observing UiEvents and if it SnackBarEvent it will show a SnackBar with a message if state is an error*/
    LaunchedEffect(key1 = true) {
        movieDetailsViewModel.getMovieDetails(movieId = movieId)

        movieDetailsViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackBarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    /**
     * Observing MovieDetailsUiState and updating UI in accordance to if the state isLoading,
     * has data or no data,error and TextField states for searching for a particular movie
     */
    val moviesDetailsUiState = movieDetailsViewModel.movieDetailsUiState.collectAsState().value

    MovieAppTheme {
        Scaffold(scaffoldState = scaffoldState) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color(0XFF080C2C)),
            ) {
                if (moviesDetailsUiState.isLoading) {
                    Box(modifier = Modifier.align(Alignment.Center)) {
                        LoadingComponent()
                    }
                }

                if (!moviesDetailsUiState.isLoading && moviesDetailsUiState.errorMessage != null) {
                    Box(modifier = Modifier.align(Alignment.Center)) {
                        ErrorComponent(errorMessage = moviesDetailsUiState.errorMessage)
                    }
                }

                if (!moviesDetailsUiState.isLoading && moviesDetailsUiState.movieDetails != null) {
                    LazyColumn {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp),
                            ) {
                                AsyncImage(
                                    model = moviesDetailsUiState.movieDetails.moviePosterUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    placeholder = painterResource(R.drawable.movie_placeholder),
                                    modifier = Modifier.height(300.dp),
                                )

                                IconButton(
                                    onClick = { navigateToHomeScreen() },
                                    modifier = Modifier.align(Alignment.TopStart),
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.ArrowBack,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(30.dp),
                                    )
                                }
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.Star,
                                        contentDescription = null,
                                        tint = Color(0XFFff9e22),
                                    )

                                    Text(
                                        text = "${moviesDetailsUiState.movieDetails.averageVote}",
                                        color = Color.White,
                                        modifier = Modifier.padding(start = 4.dp),
                                    )

                                    Spacer(modifier = Modifier.width(6.dp))

                                    Divider(
                                        modifier = Modifier
                                            .height(12.dp)
                                            .width(1.dp),
                                        color = Color.LightGray,
                                    )

                                    Spacer(modifier = Modifier.width(6.dp))

                                    Text(
                                        text = "${moviesDetailsUiState.movieDetails.totalVotes}",
                                        color = Color.LightGray,
                                    )
                                }
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = moviesDetailsUiState.movieDetails.title,
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 16.dp),
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = moviesDetailsUiState.movieDetails.genres,
                                    color = Color.LightGray,
                                    fontSize = 12.sp,
                                )

                                Spacer(modifier = Modifier.width(6.dp))

                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(Color.LightGray),
                                )

                                Spacer(modifier = Modifier.width(6.dp))

                                Text(
                                    text = moviesDetailsUiState.movieDetails.year,
                                    color = Color.LightGray,
                                )
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = moviesDetailsUiState.movieDetails.overview,
                                color = Color.White,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.padding(start = 16.dp),
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "Similar Movies",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 16.dp),
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.padding(horizontal = 16.dp),
                            ) {
                                items(moviesDetailsUiState.similarMovies) { movie ->
                                    Column {
                                        AsyncImage(
                                            model = movie.moviePosterUrl,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            placeholder = painterResource(R.drawable.movie_placeholder),
                                            modifier = Modifier
                                                .height(300.dp)
                                                .clip(
                                                    RoundedCornerShape(14.dp),
                                                ),
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Text(
                                            text = movie.title ?: "---",
                                            color = Color.White,
                                            textAlign = TextAlign.Center,
                                        )

                                        if (movie.averageVote != null) {
                                            Row(
                                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                                modifier = Modifier.padding(
                                                    top = 4.dp,
                                                    bottom = 20.dp,
                                                ),
                                            ) {
                                                Text(
                                                    text = "${movie.averageVote}",
                                                    color = Color.White,
                                                )

                                                Icon(
                                                    imageVector = Icons.Filled.Star,
                                                    contentDescription = null,
                                                    tint = Color(0XFFff9e22),
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
