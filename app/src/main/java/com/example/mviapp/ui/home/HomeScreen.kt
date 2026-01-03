package com.example.mviapp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mviapp.data.model.Movie
import com.example.mviapp.mvi.UserIntent
import com.example.mviapp.mvi.UserState

//Step 2 Create Screens
@Composable
fun HomeScreen(
    state: UserState,
    onIntent: (UserIntent) -> Unit
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
                onIntent(UserIntent.SearchMovie(it))
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
                        onIntent(UserIntent.SearchMovie(searchQuery))
                    }
                )
            }

            state.movies.isNotEmpty() -> {
                MovieList(
                    movies = state.movies,
                    onMovieClick = { movie ->
                        onIntent(UserIntent.SelectMovie(movie))
                    }
                )
            }

            else -> {
                EmptyView()
            }
        }

//        Text("Home Screen")
//        Button(onClick = { onIntent(UserIntent.SelectUser(101, "Omi")) }) {
//            Text("Go to Profile")
//        }
    }
}


@Composable
fun MovieList(
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = movies,
            key = { movie -> movie.id } // ✅ stable key
        ) { movie ->
            MovieItem(
                movie = movie,
                onClick = { onMovieClick(movie) }
            )
        }
    }
}


@Composable
fun MovieItem(
    movie: Movie,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Year: ${movie.year}",
                style = MaterialTheme.typography.bodySmall
            )
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




