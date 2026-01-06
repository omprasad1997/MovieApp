package com.example.mviapp.ui.movie.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mviapp.R
import com.example.mviapp.data.model.Movie
import com.example.mviapp.ui.movie.home.components.HeroCarousel
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TrendingMovieCard(
    movie: Movie,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Box {

            val posterModel =
                if (movie.poster == "N/A")
                    R.drawable.ic_movie_placeholder
                else movie.poster

            GlideImage(
                model = posterModel,
                contentDescription = movie.title,
                modifier = Modifier
                    .height(240.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            //Gradient Overlay
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.85f)
                            ),
                            startY = 100f
                        )
                    )
            )

            // Title + Year
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    minLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )

                Text(
                    text = movie.year,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.LightGray
                )
            }
        }
    }
}


