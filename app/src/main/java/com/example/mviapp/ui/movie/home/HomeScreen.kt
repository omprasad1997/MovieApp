package com.example.mviapp.ui.movie.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mviapp.ui.movie.home.components.HeroCarousel
import com.example.mviapp.ui.movie.home.components.TrendingMovieRow
import com.example.mviapp.ui.movie.search.ErrorView
import com.example.mviapp.ui.movie.search.LoadingView
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun HomeScreen(
    state: HomeState,
    onIntent: (HomeIntent) -> Unit,
    effect: SharedFlow<HomeEffect> ,// ✅ ADD

) {
    val snackbarHostState = remember { SnackbarHostState() }

    // 🔔 Collect one-time effects
    LaunchedEffect(Unit) {
        effect.collect { homeEffect ->
            when (homeEffect) {
                is HomeEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(homeEffect.message)
                }
                else -> Unit
            }
        }
    }

    // Initial load
    LaunchedEffect(Unit) {
        onIntent(HomeIntent.ClearError)
        onIntent(HomeIntent.LoadTrending)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        when {
            state.isLoading -> LoadingView()

            state.error != null -> ErrorView(
                message = state.error,
                onRetry = { onIntent(HomeIntent.LoadTrending) }
            )

            else -> {
                LazyColumn(
                    modifier = Modifier.padding(padding)
                ) {

                    // HERO SECTION
                    item {
                        HeroCarousel(
                            movies = state.sections.firstOrNull()?.movies.orEmpty(),
                            favouriteIds = state.favouriteIds,
                            onMovieClick = {
                                onIntent(HomeIntent.SelectMovie(it))
                            },
                            onFavouriteClick = { movie ->
                                onIntent(HomeIntent.ToggleFavourite(movie))
                            }
                        )
                    }

                    // OTHER SECTIONS
                    items(state.sections) { section ->
                        SectionTitle(section.title)

                        Spacer(modifier = Modifier.height(12.dp))

                        TrendingMovieRow(
                            movies = section.movies,
                            favouriteIds = state.favouriteIds,
                            onMovieClick = {
                                onIntent(HomeIntent.SelectMovie(it))
                            },
                            onFavouriteClick = { movie ->
                                onIntent(HomeIntent.ToggleFavourite(movie))
                            }
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                    }
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






