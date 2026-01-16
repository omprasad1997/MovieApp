package com.example.mviapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mviapp.ui.movie.favourites.FavouritesScreen
import com.example.mviapp.ui.movie.home.HomeScreenRoute
import com.example.mviapp.ui.movie.moviedetails.MovieDetailsRoute
import com.example.mviapp.ui.movie.search.SearchRoute

//Step 3 Setup Navigation
@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    val currentRoute =
        navController.currentBackStackEntryAsState()
            .value?.destination?.route

    Scaffold(
        bottomBar = {
            if (Screen.showBottomBar(currentRoute)) {
                BottomNavigationBar(navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            // HOME (placeholder)
            composable(Screen.Home.route) {
                HomeScreenRoute(navController)
            }

            //“When the app navigates to this route, show this UI.”
            composable(Screen.SearchMovie.route) { entry ->
                SearchRoute(navController)
            }

            // FAVOURITES (placeholder)
            composable(Screen.Favourites.route) {
                FavouritesScreen(
                    onMovieClick = { imdbId ->
                        navController.navigate(
                            Screen.MovieDetails.createRoute(imdbId)
                        )
                    }
                )
            }

            // MOVIE DETAILS (no bottom nav logic here)
            composable(Screen.MovieDetails.route,
                    arguments = listOf(
                        navArgument("imdbId") { type = NavType.StringType }
                    )
                ) { entry ->

                MovieDetailsRoute(navController)
            }
        }
    }
}
