package com.example.mviapp.ui.movie.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.mviapp.data.model.Movie
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun HeroCarousel(
    movies: List<Movie>,
    favouriteIds: Set<String>,              // ✅ ADD
    onMovieClick: (Movie) -> Unit,
    onFavouriteClick: (Movie) -> Unit       // ✅ ADD
) {
    if (movies.isEmpty()) return

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { movies.size }
    )

    // 🔁 Auto-scroll
    LaunchedEffect(Unit) {
        while (true) {
            delay(4000)
            val nextPage = (pagerState.currentPage + 1) % movies.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(420.dp)
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->

            val movie = movies[page]
            val isFavourite = favouriteIds.contains(movie.id) // ✅ ADD

            HeroPosterCard(
                movie = movie,
                isFavourite = isFavourite,                     // ✅ ADD
                onFavouriteClick = { onFavouriteClick(movie) },// ✅ ADD
                onClick = { onMovieClick(movie) }
            )
        }

        // Pager Indicators
        PagerIndicators(
            pageCount = movies.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp)
        )
    }
}


