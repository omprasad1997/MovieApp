package com.example.mviapp.ui.navigation

sealed class Screen(val route: String) {

    object SearchMovie : Screen("search_movie")

    object MovieDetails : Screen("movie_details/{imdbId}") {
        fun createRoute(imdbId: String): String {
            return "movie_details/$imdbId"
        }
    }
}
