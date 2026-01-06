package com.example.mviapp.ui.movie.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.mviapp.data.model.Movie

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrendingMovieRow(
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit
) {
    val listState = rememberLazyListState()

    LazyRow(
        state = listState,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = listState),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = movies,
            key = { it.id }
        ) { movie ->
            TrendingMovieCard(
                movie = movie,
                onClick = { onMovieClick(movie) }
            )
        }
    }
}