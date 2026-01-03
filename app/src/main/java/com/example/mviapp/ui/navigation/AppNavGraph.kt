package com.example.mviapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mviapp.mvi.UserEffect
import com.example.mviapp.mvi.UserIntent
import com.example.mviapp.ui.movie.SearchMovieScreen
import com.example.mviapp.ui.movie.MovieDetailsScreen
import com.example.mviapp.ui.shared.SharedViewModel

//Step 3 Setup Navigation
@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    Scaffold { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Movie.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Movie.route) { entry ->
                // Hilt ViewModel scoped to Home
                val sharedViewModel: SharedViewModel = hiltViewModel(entry)

                val state by sharedViewModel.state.collectAsState()

                LaunchedEffect(Unit) {
                    sharedViewModel.effect.collect { effect ->
                        when (effect) {
                            UserEffect.NavigateToMovieDetails ->
                                navController.navigate(Screen.MovieDetails.route)
                            else -> Unit
                        }
                    }
                }

                SearchMovieScreen(state = state, onIntent = sharedViewModel::handleIntent)
            }

            composable(Screen.MovieDetails.route) { entry ->

                val parentEntry = remember(entry) {
                    navController.getBackStackEntry(Screen.Movie.route)
                }

                val viewModel: SharedViewModel =
                    hiltViewModel(parentEntry)

                val state by viewModel.state.collectAsState()

                MovieDetailsScreen(
                    state = state,
                    onIntent = viewModel::handleIntent,
                    onBack = {
                        viewModel.handleIntent(UserIntent.BackClicked)
                        navController.popBackStack()
                    }
                )
            }

        }
    }
}
