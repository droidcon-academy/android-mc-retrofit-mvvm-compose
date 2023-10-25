package com.droidcon.movieapp.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.droidcon.movieapp.presentation.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesApp()
        }
    }

    @Composable
    fun MoviesApp() {
        MovieAppTheme {
            val navHostController = rememberNavController()

            MoviesAppNavHost(
                navHostController = navHostController,
            )
        }
    }
}
