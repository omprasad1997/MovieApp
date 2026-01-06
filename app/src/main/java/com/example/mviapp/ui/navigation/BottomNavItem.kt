package com.example.mviapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val screen: Screen,
    val title: String,
    val icon: ImageVector
) {

    object Home : BottomNavItem(
        screen = Screen.Home,
        title = "Home",
        icon = Icons.Default.Home
    )

    object Search : BottomNavItem(
        screen = Screen.SearchMovie,
        title = "Search",
        icon = Icons.Default.Search
    )

    object Favourites : BottomNavItem(
        screen = Screen.Favourites,
        title = "Favourites",
        icon = Icons.Default.Favorite
    )
}

