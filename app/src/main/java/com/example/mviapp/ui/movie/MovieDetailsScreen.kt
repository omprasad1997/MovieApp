package com.example.mviapp.ui.movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mviapp.R
import com.example.mviapp.mvi.MovieScreenIntent
import com.example.mviapp.mvi.MovieScreenState

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieDetailsScreen(
    state: MovieScreenState,
    onIntent: (MovieScreenIntent) -> Unit,
    onBack: () -> Unit
) {

    // 🔹 Trigger API call once
    LaunchedEffect(Unit) {
        onIntent(MovieScreenIntent.LoadMovieDetails)
    }

    when {
        state.isDetailsLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            ErrorView(
                message = state.error,
                onRetry = { onIntent(MovieScreenIntent.LoadMovieDetails) }
            )
        }

        state.movieDetails != null -> {
            val movie = state.movieDetails

            val posterModel =
                movie.poster ?: R.drawable.ic_movie_placeholder

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                // 🎬 Poster
                GlideImage(
                    model = posterModel,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(420.dp),
                    contentScale = ContentScale.Crop
                )

                // 🎞 Details
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "⭐ Rating: ${movie.rating}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = movie.plot,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = onBack,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Back")
                    }
                }
            }
        }
    }
}


