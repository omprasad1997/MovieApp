package com.example.mviapp.ui.movie.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mviapp.ui.navigation.Screen

@Composable
fun HomeScreenRoute(navController: NavHostController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.NavigateToMovieDetails -> {
                    navController.navigate(
                        Screen.MovieDetails.createRoute(effect.imdbId)
                    )
                }
            }
        }
    }

    HomeScreen(
        state = state,
        onIntent = viewModel::handleIntent
    )
}