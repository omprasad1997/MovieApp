package com.example.mviapp.ui.movie.moviedetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun MovieDetailsRoute(navController : NavHostController) {
    val movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel()

    val state by movieDetailsViewModel.state.collectAsState()

    MovieDetailsScreen(
        state = state,
        onIntent = movieDetailsViewModel::handleIntent,
        onBack = {
            navController.popBackStack()
        }
    )
}