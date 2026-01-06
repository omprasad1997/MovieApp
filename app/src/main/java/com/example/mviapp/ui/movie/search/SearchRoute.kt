package com.example.mviapp.ui.movie.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mviapp.ui.navigation.Screen

@Composable
fun SearchRoute(navController: NavHostController) {
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