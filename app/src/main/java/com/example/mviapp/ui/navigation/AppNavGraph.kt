package com.example.mviapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mviapp.ui.movie.moviedetails.MovieDetailsEffect
import com.example.mviapp.ui.movie.moviedetails.MovieDetailsIntent
import com.example.mviapp.ui.movie.moviedetails.MovieDetailsScreen
import com.example.mviapp.ui.movie.moviedetails.MovieDetailsViewModel
import com.example.mviapp.ui.movie.search.SearchEffect
import com.example.mviapp.ui.movie.search.SearchMovieScreen
import com.example.mviapp.ui.movie.search.SearchViewModel

//Step 3 Setup Navigation
@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    Scaffold { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.SearchMovie.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            //“When the app navigates to this route, show this UI.”
            composable(Screen.SearchMovie.route) { entry ->
                // Hilt ViewModel scoped to Home
                val searchViewModel: SearchViewModel = hiltViewModel()
                val state by searchViewModel.state.collectAsState()

                LaunchedEffect(Unit) {
                    searchViewModel.effect.collect { effect ->
                        when (effect) {
                            is SearchEffect.NavigateToMovieDetails ->
                                navController.navigate(Screen.MovieDetails.createRoute(effect.imdbId))
                            else -> Unit
                        }
                    }
                }

                SearchMovieScreen(state = state, onIntent = searchViewModel::handleIntent)
            }

            composable(Screen.MovieDetails.route,
                    arguments = listOf(
                        navArgument("imdbId") { type = NavType.StringType }
                    )
                ) { entry ->

                val movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel()

                val state by movieDetailsViewModel.state.collectAsState()

                LaunchedEffect(Unit) {
                    movieDetailsViewModel.effect.collect { effect ->
                        when (effect) {
                            MovieDetailsEffect.NavigateBack -> navController.popBackStack()
                            else -> Unit
                        }
                    }
                }

                MovieDetailsScreen(
                    state = state,
                    onIntent = movieDetailsViewModel::handleIntent,
                    onBack = {
                        movieDetailsViewModel.handleIntent(MovieDetailsIntent.BackClicked)
                    }
                )
            }
        }
    }
}
