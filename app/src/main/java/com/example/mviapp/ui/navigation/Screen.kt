package com.example.mviapp.ui.navigation

sealed class Screen(val route: String) {
    object Movie : Screen("home")
    object MovieDetails : Screen("movie_details")
}