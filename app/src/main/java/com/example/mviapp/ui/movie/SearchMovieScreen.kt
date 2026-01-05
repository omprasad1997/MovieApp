package com.example.mviapp.ui.movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mviapp.data.model.Movie
import com.example.mviapp.mvi.MovieScreenIntent
import com.example.mviapp.mvi.MovieScreenState

//Step 2 Create Screens
@Composable
fun SearchMovieScreen(
    state: MovieScreenState,
    onIntent: (MovieScreenIntent) -> Unit
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
                onIntent(MovieScreenIntent.SearchMovie(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search movies...") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.isSearchLoading -> {
                LoadingView()
            }

            state.error != null -> {
                ErrorView(
                    message = state.error,
                    onRetry = {
                        onIntent(MovieScreenIntent.SearchMovie(searchQuery))
                    }
                )
            }

            state.movies.isNotEmpty() -> {
                MovieList(
                    movies = state.movies,
                    onMovieClick = { movie ->
                        onIntent(MovieScreenIntent.SelectMovie(movie.id))
                    }
                )
            }

            else -> {
                EmptyView()
            }
        }

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


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(
    movie: Movie,
    onClick: () -> Unit
) {
    val posterUrl =
        if (movie.poster == "N/A") null else movie.poster

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .height(IntrinsicSize.Min)
        ) {

            // 🎬 Movie Poster (Glide)
            GlideImage(
                model = posterUrl,
                contentDescription = movie.title,
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Crop)

            Spacer(modifier = Modifier.width(12.dp))

            // 🎞 Movie Info
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Year: ${movie.year}",
                    style = MaterialTheme.typography.bodySmall
                )
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




