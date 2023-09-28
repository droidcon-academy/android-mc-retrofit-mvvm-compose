package com.droidcon.movieapp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.droidcon.movieapp.presentation.details.MovieDetailScreen
import com.droidcon.movieapp.presentation.home.HomeScreen

@Composable
fun MoviesAppNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = "home_screen",
    ) {
        composable(route = "home_screen") {
            HomeScreen(
                navigateToDetailsScreen = { id ->
                    navHostController.navigate(
                        route = "detail_screen/$id",
                    )
                },
            )
        }

        composable(
            route = "detail_screen/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType }),
        ) { navBackStackEntry ->

            navBackStackEntry.arguments?.getString("id")?.let {
                MovieDetailScreen(
                    id = it,
                    navigateToHomeScreen = {
                        navHostController.navigate(
                            route = "home_screen",
                        )
                    },
                )
            }
        }
    }
}
