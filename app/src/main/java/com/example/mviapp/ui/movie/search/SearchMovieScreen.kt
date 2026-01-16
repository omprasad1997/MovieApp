package com.example.mviapp.ui.movie.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mviapp.ui.movie.home.components.TrendingMovieCard

//Step 2 Create Screens
@Composable
fun SearchMovieScreen(
    state: SearchState,
    onIntent: (SearchIntent) -> Unit
) {

    var searchQuery by remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){


        // Search Box
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                onIntent(SearchIntent.SearchMovie(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search movies...") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.isLoading -> {
                LoadingView()
            }

            state.error != null -> {
                ErrorView(
                    message = state.error,
                    onRetry = {
                        onIntent(SearchIntent.SearchMovie(searchQuery))
                    }
                )
            }

            state.movies.isNotEmpty() -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // 2 or 3 based on taste
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = state.movies,
                        key = { it.id }
                    ) { movie ->

                        val isFavourite = state.favouriteIds.contains(movie.id)

                        TrendingMovieCard(
                            movie = movie,
                            isFavourite = isFavourite,
                            onFavouriteClick = {
                                onIntent(SearchIntent.ToggleFavourite(movie))
                            },
                            onClick = { onIntent(SearchIntent.SelectMovie(movie.id)) }
                        )
                    }
                }
            }

            else -> {
                EmptyView()
            }
        }

    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
fun EmptyView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Start typing to search movies 🎬")
    }
}




