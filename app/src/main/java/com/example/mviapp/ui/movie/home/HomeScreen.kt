package com.example.mviapp.ui.movie.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mviapp.ui.movie.home.components.HeroCarousel
import com.example.mviapp.ui.movie.home.components.TrendingMovieRow
import com.example.mviapp.ui.movie.search.ErrorView
import com.example.mviapp.ui.movie.search.LoadingView

@Composable
fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit
) {
    LaunchedEffect(Unit) {
        onIntent(HomeIntent.ClearError)
        onIntent(HomeIntent.LoadTrending)
    }

    when {
        state.isLoading -> LoadingView()
        state.error != null -> ErrorView(
            message = state.error,
            onRetry = { onIntent(HomeIntent.LoadTrending) }
        )
        else -> {
            LazyColumn {
                // HERO SECTION
                item {
                    HeroCarousel(
                        movies = state.sections.firstOrNull()?.movies.orEmpty(),
                        onMovieClick = {
                            onIntent(HomeIntent.SelectMovie(it))
                        }
                    )
                }

                // OTHER SECTIONS
                items(state.sections) { section ->
                    SectionTitle(section.title)

                    Spacer(modifier = Modifier.height(12.dp))

                    TrendingMovieRow(
                        movies = section.movies,
                        onMovieClick = {
                            onIntent(HomeIntent.SelectMovie(it))
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}



@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}






