package com.example.mviapp.ui.movie.favourites

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mviapp.ui.common.EmptyState
import com.example.mviapp.ui.movie.home.components.TrendingMovieCard

@Composable
fun FavouritesScreen(
    onMovieClick: (String) -> Unit,
    viewModel: FavouritesViewModel = hiltViewModel()
) {
    val movies by viewModel.favourites.collectAsState()

    if (movies.isEmpty()) {
        EmptyState(text = "No favourites yet")
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(12.dp)
        ) {
            items(movies, key = { it.id }) { movie ->
                TrendingMovieCard(
                    movie = movie,
                    isFavourite = true,
                    onClick = { onMovieClick(movie.id) }
                )
            }
        }
    }
}

