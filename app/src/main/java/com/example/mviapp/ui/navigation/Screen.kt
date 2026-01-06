package com.example.mviapp.ui.navigation

sealed class Screen(val route: String) {

    // Bottom navigation (top-level)
    object Home : Screen("home")
    object SearchMovie : Screen("search_movie")
    object Favourites : Screen("favourites")

    // Non-bottom navigation (details)
    object MovieDetails : Screen("movie_details/{imdbId}") {
        fun createRoute(imdbId: String): String {
            return "movie_details/$imdbId"
        }
    }

    companion object {
        fun showBottomBar(route: String?): Boolean {
            return route == Home.route ||
                    route == SearchMovie.route ||
                    route == Favourites.route
        }
    }
}
